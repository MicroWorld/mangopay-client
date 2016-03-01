/**
 * Copyright (C) 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.implementation;

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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.net.ssl.HttpsURLConnection;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.Error;
import org.microworld.mangopay.entities.IncomeRange;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.PersonType;
import org.microworld.mangopay.entities.Token;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;
import org.microworld.mangopay.entities.bankaccounts.BankAccountType;
import org.microworld.mangopay.entities.bankaccounts.BritishBankAccount;
import org.microworld.mangopay.entities.bankaccounts.CanadianBankAccount;
import org.microworld.mangopay.entities.bankaccounts.IbanBankAccount;
import org.microworld.mangopay.entities.bankaccounts.OtherBankAccount;
import org.microworld.mangopay.entities.bankaccounts.UsaBankAccount;
import org.microworld.mangopay.exceptions.MangopayException;
import org.microworld.mangopay.exceptions.MangopayUnauthorizedException;
import org.microworld.mangopay.implementation.serialization.CurrencyAdapter;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DefaultMangopayConnection implements MangopayConnection {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultMangopayConnection.class);
  private static final int READ_TIMEOUT = 60000;
  private static final int CONNECT_TIMEOUT = 60000;
  private static final String API_VERSION = "v2.01";
  private final String host;
  private final String clientId;
  private final String encodedAuthenticationString;
  private final Gson gson;
  private Token token;

  public DefaultMangopayConnection(final String host, final String clientId, final String passphrase) {
    this.host = Objects.requireNonNull(host, "host");
    this.clientId = Objects.requireNonNull(clientId, "clientId");
    this.encodedAuthenticationString = Base64.getEncoder().encodeToString((clientId + ":" + Objects.requireNonNull(passphrase, "passphrase")).getBytes(Charset.forName("ISO-8859-1")));
    final GsonBuilder builder = new GsonBuilder().disableHtmlEscaping();
    builder.registerTypeAdapter(MangopayUnauthorizedException.class, new MangopayUnauthorizedExceptionDeserializer());
    builder.registerTypeAdapter(Instant.class, new InstantAdapter());
    builder.registerTypeAdapter(Currency.class, new CurrencyAdapter());
    builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
    builder.registerTypeAdapter(YearMonth.class, new YearMonthAdapter());
    builder.registerTypeAdapter(IncomeRange.class, new IncomeRangeAdapter());
    this.gson = builder.create();
  }

  @Override
  public <T> List<T> queryForList(final Class<T> type, final HttpMethod method, final String path, final Filter filter, final Sort sort, final Page page, final String... pathParameters) {
    final JsonArray array = new JsonParser().parse(request(method, path, pathParameters, toQuery(filter, sort, page), null, false)).getAsJsonArray();
    final List<T> result = new ArrayList<>(array.size());
    array.forEach(e -> result.add(convert(e, type)));
    return result;
  }

  @Override
  public <T> T queryForObject(final Class<T> type, final HttpMethod method, final String path, final Object data, final String... pathParameters) {
    return convert(new JsonParser().parse(request(method, path, pathParameters, Collections.emptyMap(), data, false)), type);
  }

  @Override
  public void query(final HttpMethod method, final String path, final Object data, final String... pathParameters) {
    request(method, path, pathParameters, Collections.emptyMap(), data, false);
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
        try (OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
          final JsonObject jsonObject = gson.toJsonTree(data).getAsJsonObject();
          jsonObject.remove("Id");
          jsonObject.remove("CreationDate");
          final String json = gson.toJson(jsonObject);
          LOG.debug("Request data: {}", json);
          output.write(json);
          output.flush();
        }
      }
      final int responseCode = connection.getResponseCode();

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

  private Token getToken() {
    if (token == null || token.isExpired()) {
      final Map<String, String> data = new HashMap<>();
      data.put("grant_type", "client_credentials");
      token = gson.fromJson(request(HttpMethod.POST, "/oauth/token", new String[] {}, Collections.emptyMap(), data, true), Token.class);
    }
    return token;
  }

  private Map<String, String> toQuery(final ParameterHolder... holders) {
    return Stream.of(holders).filter(Objects::nonNull).map(ParameterHolder::getParameters).map(Map::entrySet).flatMap(Collection::stream).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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

    final String queryString = query.entrySet().stream().map(DefaultMangopayConnection::toNameValue).collect(Collectors.joining("&"));
    if (!query.isEmpty()) {
      url.append("?").append(queryString);
    }
    return new URL(url.toString());
  }

  private static String toNameValue(final Entry<String, String> entry) {
    try {
      return URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()) + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name());
    } catch (final UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  private String getContent(final InputStream inputStream) throws IOException {
    final StringBuilder content = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      String line = null;
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
    if (type.isAssignableFrom(User.class)) {
      switch (PersonType.valueOf(object.get("PersonType").getAsString())) {
        case LEGAL:
          return (T) gson.fromJson(object, LegalUser.class);
        case NATURAL:
        default:
          return (T) gson.fromJson(object, NaturalUser.class);
      }
    } else if (type.isAssignableFrom(BankAccount.class)) {
      switch (BankAccountType.valueOf(object.get("Type").getAsString())) {
        case IBAN:
          return (T) gson.fromJson(object, IbanBankAccount.class);
        case GB:
          return (T) gson.fromJson(object, BritishBankAccount.class);
        case US:
          return (T) gson.fromJson(object, UsaBankAccount.class);
        case CA:
          return (T) gson.fromJson(object, CanadianBankAccount.class);
        case OTHER:
        default:
          return (T) gson.fromJson(object, OtherBankAccount.class);
      }
    } else {
      return gson.fromJson(object, type);
    }
  }
}
