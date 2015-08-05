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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.microworld.mangopay.CardRegistrationApi;
import org.microworld.mangopay.UserApi;
import org.microworld.mangopay.entities.CardRegistration;
import org.microworld.mangopay.entities.CardRegistrationStatus;
import org.microworld.mangopay.entities.User;

public class CardRegistrationIT extends AbstractIntegrationTest {
  private static final Currency EUR = Currency.getInstance("EUR");
  private CardRegistrationApi cardRegistrationApi;

  @Before
  public void setUpCardApi() {
    cardRegistrationApi = CardRegistrationApi.createDefault(connection);
  }

  @Test
  public void createAndUpdateCardRegistration() throws MalformedURLException, IOException {
    final User user = UserApi.createDefault(connection).create(UserIT.createNaturalUser("foo@bar.com", "Foo", "Bar", "Address", LocalDate.of(1970, 1, 1), "FR", "FR", null, null, null));
    final CardRegistration cardRegistration = cardRegistrationApi.create(new CardRegistration(user.getId(), EUR));
    assertThat(cardRegistration.getStatus(), is(equalTo(CardRegistrationStatus.CREATED)));

    cardRegistration.setRegistrationData(getRegistrationData(cardRegistration.getCardRegistrationUrl(), cardRegistration.getPreregistrationData(), cardRegistration.getAccessKey()));
    final CardRegistration updatedCardRegistration = cardRegistrationApi.update(cardRegistration);
    assertThat(updatedCardRegistration.getStatus(), is(equalTo(CardRegistrationStatus.VALIDATED)));
  }

  public static String getRegistrationData(final String cardRegistrationUrl, final String preregistrationData, final String accessKey) throws MalformedURLException, IOException {
    final HttpsURLConnection connection = (HttpsURLConnection) new URL(cardRegistrationUrl).openConnection();
    connection.setDoInput(true);
    connection.setDoOutput(true);
    connection.setUseCaches(false);
    connection.setRequestMethod("POST");

    try (OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream(), Charset.forName("UTF-8"))) {
      final Map<String, String> form = new HashMap<>();
      form.put("data", preregistrationData);
      form.put("accessKeyRef", accessKey);
      form.put("cardNumber", "4970100000000154");
      form.put("cardExpirationDate", "1218");
      form.put("cardCvx", "123");
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
