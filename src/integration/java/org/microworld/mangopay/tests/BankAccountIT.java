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
import org.junit.Ignore;
import org.junit.Test;
import org.microworld.mangopay.entities.Address;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.bankaccounts.BritishBankAccount;
import org.microworld.mangopay.entities.bankaccounts.CanadianBankAccount;
import org.microworld.mangopay.entities.bankaccounts.DepositAccountType;
import org.microworld.mangopay.entities.bankaccounts.IbanBankAccount;
import org.microworld.mangopay.entities.bankaccounts.OtherBankAccount;
import org.microworld.mangopay.entities.bankaccounts.UsaBankAccount;
import org.microworld.mangopay.exceptions.MangopayException;

import io.codearte.jfairy.producer.person.Person;

public class BankAccountIT extends AbstractIntegrationTest {
  private Person person;
  private User user;

  @Before
  public void setUp() {
    person = fairy.person();
    user = client.getUserService().create(randomNaturalUser());
  }

  @Test
  public void createAndGetIbanBankAccount() {
    final Instant creationDate = Instant.now();
    final IbanBankAccount ibanBankAccount = new IbanBankAccount(person.fullName(), createAddress(person.getAddress()), "FR3020041010124530725S03383", "CRLYFRPP", fairy.textProducer().latinSentence());
    final IbanBankAccount createdBankAccount = (IbanBankAccount) client.getBankAccountService().create(user.getId(), ibanBankAccount);
    assertThat(createdBankAccount, is(ibanBankAccount(ibanBankAccount, creationDate)));

    final IbanBankAccount fetchedBankAccount = (IbanBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
    assertThat(fetchedBankAccount, is(ibanBankAccount(createdBankAccount, creationDate)));
    assertThat(fetchedBankAccount.getId(), is(equalTo(createdBankAccount.getId())));
  }

  @Test
  public void createAndGetBritishBankAccount() {
    final Instant creationDate = Instant.now();
    final BritishBankAccount britishBankAccount = new BritishBankAccount(person.fullName(), createAddress(person.getAddress()), "33333334", "070093", null);
    final BritishBankAccount createdBankAccount = (BritishBankAccount) client.getBankAccountService().create(user.getId(), britishBankAccount);
    assertThat(createdBankAccount, is(britishBankAccount(britishBankAccount, creationDate)));

    final BritishBankAccount fetchedBankAccount = (BritishBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
    assertThat(fetchedBankAccount, is(britishBankAccount(createdBankAccount, creationDate)));
    assertThat(fetchedBankAccount.getId(), is(equalTo(createdBankAccount.getId())));
  }

  @Test
  public void createAndGetCanadianBankAccount() {
    final Instant creationDate = Instant.now();
    final CanadianBankAccount canadianBankAccount = new CanadianBankAccount(person.fullName(), createAddress(person.getAddress()), "TheBank", "1337", "12345", "1234567890", "foo");
    final CanadianBankAccount createdBankAccount = (CanadianBankAccount) client.getBankAccountService().create(user.getId(), canadianBankAccount);
    assertThat(createdBankAccount, is(canadianBankAccount(canadianBankAccount, creationDate)));

    final CanadianBankAccount fetchedBankAccount = (CanadianBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
    assertThat(fetchedBankAccount, is(canadianBankAccount(createdBankAccount, creationDate)));
    assertThat(fetchedBankAccount.getId(), is(equalTo(createdBankAccount.getId())));
  }

  @Test
  public void createAndGetUsaBankAccount() {
    final Instant creationDate = Instant.now();
    final UsaBankAccount canadianBankAccount = new UsaBankAccount(person.fullName(), createAddress(person.getAddress()), "1234567890", "123456789", DepositAccountType.CHECKING, "bar");
    final UsaBankAccount createdBankAccount = (UsaBankAccount) client.getBankAccountService().create(user.getId(), canadianBankAccount);
    assertThat(createdBankAccount, is(usaBankAccount(canadianBankAccount, creationDate)));

    final UsaBankAccount fetchedBankAccount = (UsaBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
    assertThat(fetchedBankAccount, is(usaBankAccount(createdBankAccount, creationDate)));
    assertThat(fetchedBankAccount.getId(), is(equalTo(createdBankAccount.getId())));
  }

  @Test
  public void createAndGetOtherBankAccount() {
    final Instant creationDate = Instant.now();
    final OtherBankAccount otherBankAccount = new OtherBankAccount(person.fullName(), createAddress(person.getAddress()), randomCountry(), "CRLYFRPP", "1234567890", null);
    final OtherBankAccount createdBankAccount = (OtherBankAccount) client.getBankAccountService().create(user.getId(), otherBankAccount);
    assertThat(createdBankAccount, is(otherBankAccount(otherBankAccount, creationDate)));

    final OtherBankAccount fetchedBankAccount = (OtherBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
    assertThat(fetchedBankAccount, is(otherBankAccount(createdBankAccount, creationDate)));
    assertThat(fetchedBankAccount.getId(), is(equalTo(createdBankAccount.getId())));
  }

  @Test
  @Ignore
  public void createIbanBankAccountWithMissingMandatoryFields() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("OwnerAddress: The OwnerAddress field is required.");
    thrown.expectMessage("IBAN: The IBAN field is required.");
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

  @SuppressWarnings("unchecked")
  private Matcher<OtherBankAccount> otherBankAccount(final OtherBankAccount otherBankAccount, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("type", is(equalTo(otherBankAccount.getType()))),
        hasProperty("ownerName", is(equalTo(otherBankAccount.getOwnerName()))),
        hasProperty("ownerAddress", is(equalTo(otherBankAccount.getOwnerAddress()))),
        hasProperty("country", is(equalTo(otherBankAccount.getCountry()))),
        hasProperty("bic", is(equalTo(otherBankAccount.getBic()))),
        hasProperty("accountNumber", is(equalTo(otherBankAccount.getAccountNumber()))),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("tag", is(equalTo(otherBankAccount.getTag()))));
  }

  @SuppressWarnings("unchecked")
  private Matcher<BritishBankAccount> britishBankAccount(final BritishBankAccount britishBankAccount, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("type", is(equalTo(britishBankAccount.getType()))),
        hasProperty("ownerName", is(equalTo(britishBankAccount.getOwnerName()))),
        hasProperty("ownerAddress", is(equalTo(britishBankAccount.getOwnerAddress()))),
        hasProperty("accountNumber", is(equalTo(britishBankAccount.getAccountNumber()))),
        hasProperty("sortCode", is(equalTo(britishBankAccount.getSortCode()))),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("tag", is(equalTo(britishBankAccount.getTag()))));
  }

  @SuppressWarnings("unchecked")
  private Matcher<CanadianBankAccount> canadianBankAccount(final CanadianBankAccount canadianBankAccount, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("type", is(equalTo(canadianBankAccount.getType()))),
        hasProperty("ownerName", is(equalTo(canadianBankAccount.getOwnerName()))),
        hasProperty("ownerAddress", is(equalTo(canadianBankAccount.getOwnerAddress()))),
        hasProperty("bankName", is(equalTo(canadianBankAccount.getBankName()))),
        hasProperty("institutionNumber", is(equalTo(canadianBankAccount.getInstitutionNumber()))),
        hasProperty("branchCode", is(equalTo(canadianBankAccount.getBranchCode()))),
        hasProperty("accountNumber", is(equalTo(canadianBankAccount.getAccountNumber()))),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("tag", is(equalTo(canadianBankAccount.getTag()))));
  }

  @SuppressWarnings("unchecked")
  private Matcher<UsaBankAccount> usaBankAccount(final UsaBankAccount canadianBankAccount, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("type", is(equalTo(canadianBankAccount.getType()))),
        hasProperty("ownerName", is(equalTo(canadianBankAccount.getOwnerName()))),
        hasProperty("ownerAddress", is(equalTo(canadianBankAccount.getOwnerAddress()))),
        hasProperty("accountNumber", is(equalTo(canadianBankAccount.getAccountNumber()))),
        hasProperty("aba", is(equalTo(canadianBankAccount.getAba()))),
        hasProperty("depositAccountType", is(equalTo(canadianBankAccount.getDepositAccountType()))),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("tag", is(equalTo(canadianBankAccount.getTag()))));
  }
}
