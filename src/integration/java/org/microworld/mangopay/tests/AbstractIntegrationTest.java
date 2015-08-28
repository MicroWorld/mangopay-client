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
package org.microworld.mangopay.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.microworld.mangopay.MangopayClient;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.TestEnvironment;
import org.microworld.mangopay.entities.CardRegistration;
import org.microworld.mangopay.entities.User;

public class AbstractIntegrationTest {
  protected static final Currency EUR = Currency.getInstance("EUR");
  protected static final Currency USD = Currency.getInstance("USD");
  protected static final Currency XAF = Currency.getInstance("XAF");

  @Rule
  public final ExpectedException thrown = ExpectedException.none();
  protected MangopayConnection connection;
  protected MangopayClient client;

  @Before
  public void setUpConnectionAndClient() {
    connection = TestEnvironment.getInstance().getConnection();
    client = MangopayClient.createDefault(connection);
  }

  protected CardRegistration registerCard(final User user, final Currency currency, final String cardNumber, final String cardExpirationDate, final String cardCvx) throws MalformedURLException, IOException {
    CardRegistration cardRegistration = client.getCardRegistrationService().create(new CardRegistration(user.getId(), currency));
    cardRegistration.setRegistrationData(getRegistrationData(cardRegistration.getCardRegistrationUrl(), cardRegistration.getPreregistrationData(), cardRegistration.getAccessKey(), cardNumber, cardExpirationDate, cardCvx));
    cardRegistration = client.getCardRegistrationService().update(cardRegistration);
    return cardRegistration;
  }

  protected String getRegistrationData(final String cardRegistrationUrl, final String preregistrationData, final String accessKey, final String cardNumber, final String cardExpirationDate, final String cardCvx) throws MalformedURLException, IOException {
    final HttpsURLConnection connection = (HttpsURLConnection) new URL(cardRegistrationUrl).openConnection();
    connection.setDoInput(true);
    connection.setDoOutput(true);
    connection.setUseCaches(false);
    connection.setRequestMethod("POST");

    try (OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream(), Charset.forName("UTF-8"))) {
      final Map<String, String> form = new HashMap<>();
      form.put("data", preregistrationData);
      form.put("accessKeyRef", accessKey);
      form.put("cardNumber", cardNumber);
      form.put("cardExpirationDate", cardExpirationDate);
      form.put("cardCvx", cardCvx);
      output.write(form.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&")));
      output.flush();
    }

    switch (connection.getResponseCode()) {
      case HttpURLConnection.HTTP_OK:
        return getContent(connection.getInputStream());
      default:
        throw new RuntimeException(getContent(connection.getErrorStream()));
    }
  }

  private static String getContent(final InputStream inputStream) throws IOException {
    final StringBuilder content = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
      String line = null;
      while ((line = reader.readLine()) != null) {
        content.append(line).append('\n');
      }
    }
    return content.deleteCharAt(content.length() - 1).toString();
  }
}
