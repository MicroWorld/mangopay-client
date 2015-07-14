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
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import org.microworld.mangopay.Filter;
import org.microworld.mangopay.MangoPayConnection;
import org.microworld.mangopay.Page;
import org.microworld.mangopay.Sort;
import org.microworld.mangopay.entities.Error;
import org.microworld.mangopay.entities.IncomeRange;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.PersonType;
import org.microworld.mangopay.entities.Token;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.exceptions.MangoPayException;
import org.microworld.mangopay.exceptions.MangoPayUnauthorizedException;
import org.microworld.mangopay.implementation.serialization.IncomeRangeAdapter;
import org.microworld.mangopay.implementation.serialization.InstantAdapter;
import org.microworld.mangopay.implementation.serialization.LocalDateAdapter;
import org.microworld.mangopay.implementation.serialization.MangoPayUnauthorizedExceptionDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DefaultMangoPayConnection implements MangoPayConnection {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultMangoPayConnection.class);
  private static final int READ_TIMEOUT = 60000;
  private static final int CONNECT_TIMEOUT = 60000;
  private final String host;
  private final String clientId;
  private final String encodedAuthenticationString;
  private final Gson gson;
  private Token token;

  public DefaultMangoPayConnection(final String host, final String clientId, final String passphrase) {
    this.host = Objects.requireNonNull(host, "host");
    this.clientId = Objects.requireNonNull(clientId, "clientId");
    this.encodedAuthenticationString = Base64.getEncoder().encodeToString((clientId + ":" + Objects.requireNonNull(passphrase, "passphrase")).getBytes(Charset.forName("ISO-8859-1")));
    final GsonBuilder builder = new GsonBuilder().disableHtmlEscaping();
    builder.registerTypeAdapter(MangoPayUnauthorizedException.class, new MangoPayUnauthorizedExceptionDeserializer());
    builder.registerTypeAdapter(Instant.class, new InstantAdapter());
    builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
    builder.registerTypeAdapter(IncomeRange.class, new IncomeRangeAdapter());
    this.gson = builder.create();
  }

  @Override
  public <T> List<T> queryForList(final Class<T> type, final HttpMethod method, final String path, final Filter filter, final Sort sort, final Page page) {
    final JsonArray array = new JsonParser().parse(request(method, path, new String[] {}, toQuery(filter, sort, page), null, false)).getAsJsonArray();
    final List<T> result = new ArrayList<>(array.size());
    array.forEach(e -> result.add(convert(e, type)));
    return result;
  }

  @Override
  public <T> T queryForObject(final Class<T> type, final HttpMethod method, final String path, final Object data, final String... pathParameters) {
    return convert(new JsonParser().parse(request(method, path, pathParameters, Collections.emptyMap(), data, false)), type);
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
        try (OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream(), Charset.forName("UTF-8"))) {
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
          throw gson.fromJson(getContent(connection.getErrorStream()), MangoPayUnauthorizedException.class);
        case HttpURLConnection.HTTP_OK:
        case HttpURLConnection.HTTP_NO_CONTENT:
          return getContent(connection.getInputStream());
        default:
          throw new MangoPayException(gson.fromJson(getContent(connection.getErrorStream()), Error.class));
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Token getToken() throws IOException {
    if (token == null || token.isExpired()) {
      final Map<String, String> data = new HashMap<>();
      data.put("grant_type", "client_credentials");
      token = gson.fromJson(request(HttpMethod.POST, "/oauth/token", new String[] {}, Collections.emptyMap(), data, true), Token.class);
    }
    return token;
  }

  private Map<String, String> toQuery(final Filter filter, final Sort sort, final Page page) {
    final Map<String, String> query = new HashMap<>();
    if (filter != null) {
      query.putAll(filter.getParameters());
    }
    if (sort != null) {
      query.putAll(sort.getParameters());
    }
    if (page != null) {
      query.putAll(page.getParameters());
    }
    return query;
  }

  private URL createUrl(final boolean isAuthorizationRequest, final String path, final String[] pathParameters, final Map<String, String> query) throws MalformedURLException {
    final StringBuilder url = new StringBuilder("https://");
    url.append(host);
    url.append("/v2");
    if (!isAuthorizationRequest) {
      url.append("/").append(clientId);
    }

    String parsedPath = path;
    for (int i = 0; i < pathParameters.length; i++) {
      parsedPath = parsedPath.replace("{" + i + "}", pathParameters[i]);
    }
    url.append(parsedPath);

    final String queryString = query.entrySet().stream().map(DefaultMangoPayConnection::toNameValue).collect(Collectors.joining("&"));
    if (!query.isEmpty()) {
      url.append("?").append(queryString);
    }
    return new URL(url.toString());
  }

  private static String toNameValue(final Entry<String, String> entry) {
    try {
      return URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
    } catch (final UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  private String getContent(final InputStream inputStream) throws IOException {
    final StringBuilder content = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
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
      return PersonType.LEGAL.equals(PersonType.valueOf(object.get("PersonType").getAsString())) ? (T) gson.fromJson(object, LegalUser.class) : (T) gson.fromJson(object, NaturalUser.class);
    } else {
      return gson.fromJson(object, type);
    }
  }
}
