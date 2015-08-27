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

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Currency;

import org.junit.Test;
import org.microworld.mangopay.entities.CardRegistration;
import org.microworld.mangopay.entities.CardRegistrationStatus;
import org.microworld.mangopay.entities.User;

public class CardRegistrationIT extends AbstractIntegrationTest {
  private static final Currency EUR = Currency.getInstance("EUR");

  @Test
  public void createAndUpdateCardRegistration() throws MalformedURLException, IOException {
    final User user = client.getUserService().create(UserIT.createNaturalUser("foo@bar.com", "Foo", "Bar", "Address", LocalDate.of(1970, 1, 1), "FR", "FR", null, null, null));
    final CardRegistration cardRegistration = client.getCardRegistrationService().create(new CardRegistration(user.getId(), EUR));
    assertThat(cardRegistration.getStatus(), is(equalTo(CardRegistrationStatus.CREATED)));

    cardRegistration.setRegistrationData(getRegistrationData(cardRegistration.getCardRegistrationUrl(), cardRegistration.getPreregistrationData(), cardRegistration.getAccessKey(), "4970100000000154", "1218", "123"));
    final CardRegistration updatedCardRegistration = client.getCardRegistrationService().update(cardRegistration);
    assertThat(updatedCardRegistration.getStatus(), is(equalTo(CardRegistrationStatus.VALIDATED)));
  }
}
