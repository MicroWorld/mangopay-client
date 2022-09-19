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
package org.microworld.mangopay.tests;

import io.codearte.jfairy.producer.person.Person;
import org.junit.Before;
import org.junit.Test;
import org.microworld.mangopay.entities.Address;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;
import org.microworld.mangopay.entities.bankaccounts.BritishBankAccount;
import org.microworld.mangopay.entities.bankaccounts.CanadianBankAccount;
import org.microworld.mangopay.entities.bankaccounts.DepositAccountType;
import org.microworld.mangopay.entities.bankaccounts.IbanBankAccount;
import org.microworld.mangopay.entities.bankaccounts.OtherBankAccount;
import org.microworld.mangopay.entities.bankaccounts.UsaBankAccount;
import org.microworld.mangopay.exceptions.MangopayException;
import org.microworld.mangopay.search.Page;

import java.time.Instant;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.microworld.test.Matchers.bankAccount;

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
        final IbanBankAccount ibanBankAccount = new IbanBankAccount(person.getFullName(), createAddress(person.getAddress()), "FR3020041010124530725S03383", "CRLYFRPP", fairy.textProducer().latinSentence());
        final IbanBankAccount createdBankAccount = (IbanBankAccount) client.getBankAccountService().create(user.getId(), ibanBankAccount);
        assertThat(createdBankAccount, is(bankAccount(ibanBankAccount, true, creationDate)));

        final IbanBankAccount fetchedBankAccount = (IbanBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
        assertThat(fetchedBankAccount, is(bankAccount(createdBankAccount)));
    }

    @Test
    public void createAndGetBritishBankAccount() {
        final Instant creationDate = Instant.now();
        final BritishBankAccount britishBankAccount = new BritishBankAccount(person.getFullName(), createAddress(person.getAddress()), "33333334", "070093", null);
        final BritishBankAccount createdBankAccount = (BritishBankAccount) client.getBankAccountService().create(user.getId(), britishBankAccount);
        assertThat(createdBankAccount, is(bankAccount(britishBankAccount, true, creationDate)));

        final BritishBankAccount fetchedBankAccount = (BritishBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
        assertThat(fetchedBankAccount, is(bankAccount(createdBankAccount)));
    }

    @Test
    public void createAndGetCanadianBankAccount() {
        final Instant creationDate = Instant.now();
        final CanadianBankAccount canadianBankAccount = new CanadianBankAccount(person.getFullName(), createAddress(person.getAddress()), "TheBank", "666", "12345", "1234567890", "foo");
        final CanadianBankAccount createdBankAccount = (CanadianBankAccount) client.getBankAccountService().create(user.getId(), canadianBankAccount);
        assertThat(createdBankAccount, is(bankAccount(canadianBankAccount, true, creationDate)));

        final CanadianBankAccount fetchedBankAccount = (CanadianBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
        assertThat(fetchedBankAccount, is(bankAccount(createdBankAccount)));
    }

    @Test
    public void createAndGetUsaBankAccount() {
        final Instant creationDate = Instant.now();
        final UsaBankAccount usaBankAccount = new UsaBankAccount(person.getFullName(), createAddress(person.getAddress()), "1234567890", "123456789", DepositAccountType.CHECKING, "bar");
        final UsaBankAccount createdBankAccount = (UsaBankAccount) client.getBankAccountService().create(user.getId(), usaBankAccount);
        assertThat(createdBankAccount, is(bankAccount(usaBankAccount, true, creationDate)));

        final UsaBankAccount fetchedBankAccount = (UsaBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
        assertThat(fetchedBankAccount, is(bankAccount(createdBankAccount)));
    }

    @Test
    public void createAndGetOtherBankAccount() {
        final Instant creationDate = Instant.now();
        final OtherBankAccount otherBankAccount = new OtherBankAccount(person.getFullName(), createAddress(person.getAddress()), randomCountry(), "CRLYFRPP", "1234567890", null);
        final OtherBankAccount createdBankAccount = (OtherBankAccount) client.getBankAccountService().create(user.getId(), otherBankAccount);
        assertThat(createdBankAccount, is(bankAccount(otherBankAccount, true, creationDate)));

        final OtherBankAccount fetchedBankAccount = (OtherBankAccount) client.getBankAccountService().get(user.getId(), createdBankAccount.getId());
        assertThat(fetchedBankAccount, is(bankAccount(createdBankAccount)));
    }

    @Test
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
        final Address address = new Address("Street", null, "City", null, "WX123", "FR");
        client.getBankAccountService().create(user.getId(), new IbanBankAccount("Foo", address, "FAKE_IBAN_NUMBER", null, null));
    }

    @Test
    public void getBankAccountWithInvalidId() {
        // This one is not very consistent with the rest of the API. Usually we get a "ressource_not_found" message.
        thrown.expect(MangopayException.class);
        thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
        thrown.expectMessage("BankAccountId: The value 10 is not valid");
        client.getBankAccountService().get(user.getId(), "10");
    }

    @Test
    public void listBankAccountsOfUserWithoutBankAccounts() {
        final List<BankAccount> bankAccounts = client.getBankAccountService().list(user.getId(), Page.of(1));
        assertThat(bankAccounts, is(empty()));
    }

    @Test
    public void listBankAccountsOfUserWithBankAccounts() {
        final IbanBankAccount ibanBankAccount = (IbanBankAccount) client.getBankAccountService().create(user.getId(), new IbanBankAccount(person.getFullName(), createAddress(person.getAddress()), "FR3020041010124530725S03383", "CRLYFRPP", fairy.textProducer().latinSentence()));
        final BritishBankAccount britishBankAccount = (BritishBankAccount) client.getBankAccountService().create(user.getId(), new BritishBankAccount(person.getFullName(), createAddress(person.getAddress()), "33333334", "070093", null));
        final CanadianBankAccount canadianBankAccount = (CanadianBankAccount) client.getBankAccountService().create(user.getId(), new CanadianBankAccount(person.getFullName(), createAddress(person.getAddress()), "TheBank", "666", "12345", "1234567890", "foo"));
        final UsaBankAccount usaBankAccount = (UsaBankAccount) client.getBankAccountService().create(user.getId(), new UsaBankAccount(person.getFullName(), createAddress(person.getAddress()), "1234567890", "123456789", DepositAccountType.CHECKING, "bar"));
        final OtherBankAccount otherBankAccount = (OtherBankAccount) client.getBankAccountService().create(user.getId(), new OtherBankAccount(person.getFullName(), createAddress(person.getAddress()), randomCountry(), "CRLYFRPP", "1234567890", null));
        final List<BankAccount> bankAccounts = client.getBankAccountService().list(user.getId(), Page.of(1));
        assertThat(bankAccounts, containsInAnyOrder(asList(bankAccount(ibanBankAccount), bankAccount(britishBankAccount), bankAccount(canadianBankAccount), bankAccount(usaBankAccount), bankAccount(otherBankAccount))));
    }

    @Test
    public void listBankAccountsOfInvalidUser() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("ressource_not_found: The ressource does not exist");
        thrown.expectMessage("RessourceNotFound: Cannot found the ressource User with the id=10");
        client.getBankAccountService().list("10", Page.of(1));
    }

    @Test
    public void deactivateBankAccount() {
        final IbanBankAccount createdBankAccount = (IbanBankAccount) client.getBankAccountService().create(user.getId(), new IbanBankAccount(person.getFullName(), createAddress(person.getAddress()), "FR3020041010124530725S03383", "CRLYFRPP", fairy.textProducer().latinSentence()));
        assertTrue(createdBankAccount.isActive());
        assertFalse(client.getBankAccountService().deactivate(user.getId(), createdBankAccount.getId()).isActive());
    }

    @Test
    public void deactivatingBankAccountSeveralTimesDoesNotThrowExceptions() {
        final IbanBankAccount createdBankAccount = (IbanBankAccount) client.getBankAccountService().create(user.getId(), new IbanBankAccount(person.getFullName(), createAddress(person.getAddress()), "FR3020041010124530725S03383", "CRLYFRPP", fairy.textProducer().latinSentence()));
        assertTrue(createdBankAccount.isActive());
        assertFalse(client.getBankAccountService().deactivate(user.getId(), createdBankAccount.getId()).isActive());
        assertFalse(client.getBankAccountService().deactivate(user.getId(), createdBankAccount.getId()).isActive());
    }
}
