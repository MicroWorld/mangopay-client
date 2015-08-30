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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.microworld.test.Matchers.around;

import java.time.Instant;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.microworld.mangopay.entities.Address;
import org.microworld.mangopay.entities.IbanBankAccount;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.exceptions.MangopayException;

import io.codearte.jfairy.producer.person.Person;

public class BankAccountIT extends AbstractIntegrationTest {
  private User user;

  @Before
  public void setUpUserAccount() {
    user = client.getUserService().create(randomNaturalUser());
  }

  @Test
  public void createAndGetIbanBankAccount() {
    final Instant creationDate = Instant.now();
    final IbanBankAccount ibanBankAccount = randomIbanBankAccount();
    final IbanBankAccount createdBankAccount = (IbanBankAccount) client.getBankAccountService().create(user.getId(), ibanBankAccount);
    assertThat(createdBankAccount, is(ibanBankAccount(ibanBankAccount, creationDate)));

    final IbanBankAccount fetchedBankAccount = (IbanBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
    assertThat(fetchedBankAccount, is(ibanBankAccount(createdBankAccount, creationDate)));
    assertThat(fetchedBankAccount.getId(), is(equalTo(createdBankAccount.getId())));
  }

  @Test
  public void createIbanBankAccountWithMissingMandatoryFields() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("OwnerAddress: The OwnerAddress field is required.");
    thrown.expectMessage("IBAN: The field IBAN must match the regular expression '^[a-zA-Z]{2}\\d{2}\\s*(\\w{4}\\s*){2,7}\\w{1,4}\\s*$'.");
    client.getBankAccountService().create(user.getId(), new IbanBankAccount("Foo", null, null, null, null));
  }

  @Test
  public void createIbanBankAccountWithFieldsContainingInvalidValues() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("IBAN: The field IBAN must match the regular expression '^[a-zA-Z]{2}\\d{2}\\s*(\\w{4}\\s*){2,7}\\w{1,4}\\s*$'.");
    final Address address = new Address();
    address.setAddressLine1("Street");
    address.setCity("City");
    address.setCountry("FR");
    address.setPostalCode("WX123");
    client.getBankAccountService().create(user.getId(), new IbanBankAccount("Foo", address, "FAKE_IBAN_NUMBER", null, null));
  }

  @Test
  public void getBankAccountWithInvalidId() {
    // This one is not very consistent with the rest of the API. Usually we get a "ressource_not_found" message.
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("BankAccountId: The BankAccountId is incorrect");
    client.getBankAccountService().get(user.getId(), "10");
  }

  private IbanBankAccount randomIbanBankAccount() {
    final Person person = fairy.person();
    // String iban = new IBANProvider(fairy.baseProducer()).get().getIbanNumber();
    final String iban = "FR3020041010124530725S03383";
    final String bic = "CRLYFRPP";
    return new IbanBankAccount(person.fullName(), createAddress(person.getAddress()), iban, bic, fairy.textProducer().latinSentence());
  }

  @SuppressWarnings("unchecked")
  private Matcher<IbanBankAccount> ibanBankAccount(final IbanBankAccount ibanBankAccount, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("type", is(equalTo(ibanBankAccount.getType()))),
        hasProperty("ownerName", is(equalTo(ibanBankAccount.getOwnerName()))),
        hasProperty("ownerAddress", is(equalTo(ibanBankAccount.getOwnerAddress()))),
        hasProperty("iban", is(equalTo(ibanBankAccount.getIban()))),
        hasProperty("bic", is(equalTo(ibanBankAccount.getBic()))),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("tag", is(equalTo(ibanBankAccount.getTag()))));
  }
}
