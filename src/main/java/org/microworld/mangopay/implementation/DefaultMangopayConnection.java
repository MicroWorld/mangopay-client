/**
 * Copyright © 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.tuple.Pair;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.Amount;
import org.microworld.mangopay.entities.BankWirePayIn;
import org.microworld.mangopay.entities.BankWirePayOut;
import org.microworld.mangopay.entities.DirectCardPayIn;
import org.microworld.mangopay.entities.Error;
import org.microworld.mangopay.entities.ExecutionType;
import org.microworld.mangopay.entities.IncomeRange;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.PayIn;
import org.microworld.mangopay.entities.PayInType;
import org.microworld.mangopay.entities.PayOut;
import org.microworld.mangopay.entities.PersonType;
import org.microworld.mangopay.entities.RateLimit;
import org.microworld.mangopay.entities.RateLimitInterval;
import org.microworld.mangopay.entities.Token;
import org.microworld.mangopay.entities.Transaction;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.WebCardPayIn;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;
import org.microworld.mangopay.entities.bankaccounts.BankAccountType;
import org.microworld.mangopay.entities.bankaccounts.BritishBankAccount;
import org.microworld.mangopay.entities.bankaccounts.CanadianBankAccount;
import org.microworld.mangopay.entities.bankaccounts.IbanBankAccount;
import org.microworld.mangopay.entities.bankaccounts.OtherBankAccount;
import org.microworld.mangopay.entities.bankaccounts.UsaBankAccount;
import org.microworld.mangopay.exceptions.MangopayException;
import org.microworld.mangopay.exceptions.MangopayUnauthorizedException;
import org.microworld.mangopay.implementation.serialization.AmountAdapter;
import org.microworld.mangopay.implementation.serialization.IncomeRangeAdapter;
import org.microworld.mangopay.implementation.serialization.InstantAdapter;
import org.microworld.mangopay.implementation.serialization.LocalDateAdapter;
import org.microworld.mangopay.implementation.serialization.MangopayUnauthorizedExceptionDeserializer;
import org.microworld.mangopay.implementation.serialization.YearMonthAdapter;
import org.microworld.mangopay.misc.HttpMethod;
import org.microworld.mangopay.search.Filter;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.ParameterHolder;
import org.microworld.mangopay.search.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Locale.ENGLISH;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static org.microworld.mangopay.misc.Reflections.setFieldValue;

public class DefaultMangopayConnection implements MangopayConnection {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultMangopayConnection.class);
    private static final int READ_TIMEOUT = 60000;
    private static final int CONNECT_TIMEOUT = 60000;
    private static final String API_VERSION = "v2.01";
    private final String host;
    private final String clientId;
    private final String encodedAuthenticationString;
    private final Map<RateLimitInterval, RateLimit> rateLimits;
    private final Gson gson;
    private Token token;

    public DefaultMangopayConnection(final String host, final String clientId, final String passphrase) {
        this.host = requireNonNull(host, "The host must not be null.");
        this.clientId = requireNonNull(clientId, "The clientId must not be null.");
        this.encodedAuthenticationString = encodeAuthenticationString(clientId, passphrase);
        this.rateLimits = new EnumMap<>(RateLimitInterval.class);
        final GsonBuilder builder = new GsonBuilder().disableHtmlEscaping();
        builder.registerTypeAdapter(MangopayUnauthorizedException.class, new MangopayUnauthorizedExceptionDeserializer());
        builder.registerTypeAdapter(Instant.class, new InstantAdapter());
        builder.registerTypeAdapter(Amount.class, new AmountAdapter());
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        builder.registerTypeAdapter(YearMonth.class, new YearMonthAdapter());
        builder.registerTypeAdapter(IncomeRange.class, new IncomeRangeAdapter());
        this.gson = builder.create();
    }

    private static String toNameValue(final Entry<String, String> entry) {
        try {
            return URLEncoder.encode(entry.getKey(), ISO_8859_1.name()) + "=" + URLEncoder.encode(entry.getValue(), ISO_8859_1.name());
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    static String encodeAuthenticationString(final String clientId, final String passphrase) {
        return Base64.getEncoder().encodeToString((clientId + ":" + requireNonNull(passphrase, "The passphrase must not be null.")).getBytes(ISO_8859_1));
    }

    static void parseRateLimits(final Map<RateLimitInterval, RateLimit> limits, final Map<String, List<String>> rawHeaders) {
        final int numberOfIntervals = RateLimitInterval.values().length;
        final Map<String, List<String>> rateLimitHeaders = rawHeaders.entrySet().stream().filter(e -> e.getKey() != null)
                .map(e -> Pair.of(e.getKey().toLowerCase(ENGLISH), e.getValue()))
                .filter(p -> p.getKey().startsWith("x-ratelimit")).collect(toMap(Pair::getKey, Pair::getValue));
        final List<String> madeList = rateLimitHeaders.getOrDefault("x-ratelimit", emptyList());
        final List<String> remainingList = rateLimitHeaders.getOrDefault("x-ratelimit-remaining", emptyList());
        final List<String> resetList = rateLimitHeaders.getOrDefault("x-ratelimit-reset", emptyList());
        if (resetList.size() >= numberOfIntervals && remainingList.size() >= numberOfIntervals && madeList.size() >= numberOfIntervals) {
            for (final RateLimitInterval interval : RateLimitInterval.values()) {
                final int callsMade = Integer.parseInt(madeList.get(numberOfIntervals - 1 - interval.ordinal()));
                final int callsRemaining = Integer.parseInt(remainingList.get(numberOfIntervals - 1 - interval.ordinal()));
                final Instant reset = Instant.ofEpochSecond(Long.parseLong(resetList.get(numberOfIntervals - 1 - interval.ordinal())));
                limits.put(interval, new RateLimit(interval, callsMade, callsRemaining, reset));
            }
        }
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public RateLimit getRateLimit(final RateLimitInterval interval) {
        final RateLimit limit = rateLimits.get(interval);
        if (limit == null) {
            request(HttpMethod.GET, "/clients", new String[]{}, emptyMap(), null, false);
            return rateLimits.get(interval);
        } else {
            return limit;
        }
    }

    @Override
    public <T> List<T> queryForList(final Class<T> type, final HttpMethod method, final String path, final Filter filter, final Sort sort, final Page page, final String... pathParameters) {
        final JsonArray array = JsonParser.parseString(request(method, path, pathParameters, toQuery(filter, sort, page), null, false)).getAsJsonArray();
        final List<T> result = new ArrayList<>(array.size());
        array.forEach(e -> result.add(convert(e, type)));
        return result;
    }

    @Override
    public <T> T queryForObject(final Class<T> type, final HttpMethod method, final String path, final Object data, final String... pathParameters) {
        return convert(JsonParser.parseString(request(method, path, pathParameters, emptyMap(), data, false)), type);
    }

    @Override
    public void query(final HttpMethod method, final String path, final Object data, final String... pathParameters) {
        request(method, path, pathParameters, emptyMap(), data, false);
    }

    private String request(final HttpMethod method, final String path, final String[] pathParameters, final Map<String, String> query, final Object data, final boolean isAuthorizationRequest) {
        try {
            final Map<String, String> headers = new HashMap<>();
            if (isAuthorizationRequest) {
                headers.put("Host", host);
                headers.put("Authorization", "Basic " + encodedAuthenticationString);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
            } else {
                headers.put(Token.HEADER_NAME, getToken().getHeaderValue());
                headers.put("Content-Type", "application/json");
            }

            final URL url = createUrl(isAuthorizationRequest, path, pathParameters, query);
            final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestMethod(method.name());
            headers.forEach(connection::addRequestProperty);

            LOG.debug("Request: {} {}", connection.getRequestMethod(), url);
            if (data != null) {
                try (OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream(), UTF_8)) {
                    final String json = data instanceof String ? (String) data : toJson(data);
                    LOG.debug("Request data: {}", json);
                    output.write(json);
                    output.flush();
                }
            }
            final int responseCode = connection.getResponseCode();
            parseRateLimits(rateLimits, connection.getHeaderFields());

            LOG.debug("Response code: {}", responseCode);
            switch (responseCode) {
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    throw new RuntimeException("Internal Server Error");
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    throw gson.fromJson(getContent(connection.getErrorStream()), MangopayUnauthorizedException.class);
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_NO_CONTENT:
                    return getContent(connection.getInputStream());
                default:
                    throw new MangopayException(gson.fromJson(getContent(connection.getErrorStream()), Error.class));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJson(final Object data) {
        final JsonObject jsonObject = gson.toJsonTree(data).getAsJsonObject();
        jsonObject.remove("Id");
        jsonObject.remove("CreationDate");
        return gson.toJson(jsonObject);
    }

    private Token getToken() {
        if (token == null || token.isExpired()) {
            final Map<String, String> data = new HashMap<>();
            data.put("grant_type", "client_credentials");
            token = gson.fromJson(request(HttpMethod.POST, "/oauth/token", new String[]{}, emptyMap(), data, true), Token.class);
        }
        return token;
    }

    private Map<String, String> toQuery(final ParameterHolder... holders) {
        return Stream.of(holders).filter(Objects::nonNull).map(ParameterHolder::getParameters).map(Map::entrySet).flatMap(Collection::stream).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private URL createUrl(final boolean isAuthorizationRequest, final String path, final String[] pathParameters, final Map<String, String> query) throws MalformedURLException {
        final StringBuilder url = new StringBuilder("https://");
        url.append(host);
        url.append("/").append(API_VERSION);
        if (!isAuthorizationRequest) {
            url.append("/").append(clientId);
        }

        String parsedPath = path;
        for (int i = 0; i < pathParameters.length; i++) {
            parsedPath = parsedPath.replace("{" + i + "}", pathParameters[i]);
        }
        url.append(parsedPath);

        final String queryString = query.entrySet().stream().map(DefaultMangopayConnection::toNameValue).collect(joining("&"));
        if (!query.isEmpty()) {
            url.append("?").append(queryString);
        }
        return new URL(url.toString());
    }

    private String getContent(final InputStream inputStream) throws IOException {
        final StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }
        }
        LOG.debug("Response content: {}", content);
        return content.toString();
    }

    @SuppressWarnings("unchecked")
    private <T> T convert(final JsonElement element, final Class<T> type) {
        final JsonObject object = element.getAsJsonObject();
        if (type.equals(User.class)) {
            return (T) convertUser(object);
        } else if (type.equals(BankAccount.class)) {
            return (T) convertBankAccount(object);
        } else if (type.equals(PayOut.class)) {
            return (T) convert(object, BankWirePayOut.class);
        } else if (type.equals(PayIn.class)) {
            return (T) convertPayIn(object);
        } else if (type.equals(BankWirePayIn.class)) {
            return (T) convertBankWirePayIn(object);
        } else if (type.equals(Transaction.class)) {
            return (T) convertTransaction(object);
        } else {
            return gson.fromJson(object, type);
        }
    }

    private Transaction convertTransaction(final JsonObject object) {
        // See https://github.com/Mangopay/mangopay/issues/13 about why this code is not used.
        // switch (TransactionType.valueOf(object.get("Type").getAsString())) {
        // case PAYIN:
        // return convert(object, PayIn.class);
        // case PAYOUT:
        // return convert(object, PayOut.class);
        // case TRANSFER:
        // default:
        // return convert(object, Transfer.class);
        // }
        return gson.fromJson(object, Transaction.class);
    }

    private PayIn convertPayIn(final JsonObject object) {
        switch (PayInType.valueOf(object.get("PaymentType").getAsString())) {
            case BANK_WIRE:
                return convertBankWirePayIn(object);
            case CARD:
            default:
                return convertCardPayIn(object);
        }
    }

    private PayIn convertCardPayIn(final JsonObject object) {
        switch (ExecutionType.valueOf(object.get("ExecutionType").getAsString())) {
            case DIRECT:
                return gson.fromJson(object, DirectCardPayIn.class);
            case WEB:
            default:
                return gson.fromJson(object, WebCardPayIn.class);
        }
    }

    private PayIn convertBankWirePayIn(final JsonObject object) {
        final JsonObject bankAccount = object.remove("BankAccount").getAsJsonObject();
        final BankWirePayIn payIn = gson.fromJson(object, BankWirePayIn.class);
        setFieldValue(BankWirePayIn.class, "bankAccount", payIn, convertBankAccount(bankAccount));
        return payIn;
    }

    private BankAccount convertBankAccount(final JsonObject object) {
        switch (BankAccountType.valueOf(object.get("Type").getAsString())) {
            case IBAN:
                return gson.fromJson(object, IbanBankAccount.class);
            case GB:
                return gson.fromJson(object, BritishBankAccount.class);
            case US:
                return gson.fromJson(object, UsaBankAccount.class);
            case CA:
                return gson.fromJson(object, CanadianBankAccount.class);
            case OTHER:
            default:
                return gson.fromJson(object, OtherBankAccount.class);
        }
    }

    private User convertUser(final JsonObject object) {
        switch (PersonType.valueOf(object.get("PersonType").getAsString())) {
            case LEGAL:
                return gson.fromJson(object, LegalUser.class);
            case NATURAL:
            default:
                return gson.fromJson(object, NaturalUser.class);
        }
    }
}
