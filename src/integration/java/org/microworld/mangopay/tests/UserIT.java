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
import org.hamcrest.Matcher;
import org.junit.Test;
import org.microworld.mangopay.entities.BrowserInfo;
import org.microworld.mangopay.entities.Card;
import org.microworld.mangopay.entities.CardRegistration;
import org.microworld.mangopay.entities.DirectCardPayIn;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.PersonType;
import org.microworld.mangopay.entities.SecureMode;
import org.microworld.mangopay.entities.Transaction;
import org.microworld.mangopay.entities.TransactionType;
import org.microworld.mangopay.entities.Transfer;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;
import org.microworld.mangopay.entities.bankaccounts.BritishBankAccount;
import org.microworld.mangopay.entities.bankaccounts.IbanBankAccount;
import org.microworld.mangopay.entities.kyc.KycDocument;
import org.microworld.mangopay.entities.kyc.KycDocumentType;
import org.microworld.mangopay.entities.kyc.KycLevel;
import org.microworld.mangopay.exceptions.MangopayException;
import org.microworld.mangopay.search.Filter;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static java.util.Arrays.asList;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Durations.TWO_SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.microworld.mangopay.search.SortDirection.ASCENDING;
import static org.microworld.mangopay.search.SortDirection.DESCENDING;
import static org.microworld.mangopay.search.SortField.CREATION_DATE;
import static org.microworld.test.Matchers.after;
import static org.microworld.test.Matchers.around;
import static org.microworld.test.Matchers.before;

public class UserIT extends AbstractIntegrationTest {
    @Test
    public void createGetUpdateNaturalUser() {
        final Instant creationDate = Instant.now();
        final NaturalUser naturalUser = randomNaturalUser();
        final NaturalUser createdUser = client.getUserService().create(naturalUser);
        assertThat(createdUser, is(naturalUser(naturalUser, KycLevel.LIGHT, creationDate)));

        final NaturalUser fetchedUser = client.getUserService().getNaturalUser(createdUser.getId());
        assertThat(fetchedUser, is(naturalUser(naturalUser, KycLevel.LIGHT, creationDate)));
        assertThat(fetchedUser.getId(), is(equalTo(createdUser.getId())));

        fetchedUser.setTag("Good user");
        final NaturalUser updatedUser = client.getUserService().update(fetchedUser);
        assertThat(updatedUser, is(naturalUser(fetchedUser, KycLevel.LIGHT, creationDate)));
        assertThat(updatedUser.getId(), is(equalTo(fetchedUser.getId())));

        final User user = client.getUserService().get(updatedUser.getId());
        assertThat((NaturalUser) user, is(naturalUser(updatedUser, KycLevel.LIGHT, creationDate)));
        assertThat(user.getId(), is(equalTo(updatedUser.getId())));
    }

    @Test
    public void createNaturalUserWithMissingMandatoryFields() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
        thrown.expectMessage("Email: The Email field is required.");
//        thrown.expectMessage("Birthday: The Birthday field is required.");
        final NaturalUser naturalUser = randomNaturalUser();
        naturalUser.setEmail(null);
        naturalUser.setBirthday(null);
        client.getUserService().create(naturalUser);
    }

    @Test
    public void createNaturalUserWithFieldsContainingInvalidValues() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
        thrown.expectMessage("Nationality: The requested value is not an ISO 3166-1 alpha-2 code which is expected");
        final NaturalUser naturalUser = randomNaturalUser();
        naturalUser.setNationality("USA");
        client.getUserService().create(naturalUser);
    }

    @Test
    public void createGetUpdateLegalUser() {
        final Instant creationDate = Instant.now();
        final LegalUser legalUser = randomLegalUser();
        final LegalUser createdUser = client.getUserService().create(legalUser);
        assertThat(createdUser, is(legalUser(legalUser, KycLevel.LIGHT, creationDate)));

        final LegalUser fetchedUser = client.getUserService().getLegalUser(createdUser.getId());
        assertThat(fetchedUser, is(legalUser(createdUser, KycLevel.LIGHT, creationDate)));
        assertThat(fetchedUser.getId(), is(equalTo(createdUser.getId())));

        fetchedUser.setTag("Good user");
        final LegalUser updatedUser = client.getUserService().update(fetchedUser);
        assertThat(updatedUser, is(legalUser(fetchedUser, KycLevel.LIGHT, creationDate)));
        assertThat(updatedUser.getId(), is(equalTo(fetchedUser.getId())));

        final User user = client.getUserService().get(updatedUser.getId());
        assertThat((LegalUser) user, is(legalUser(updatedUser, KycLevel.LIGHT, creationDate)));
        assertThat(user.getId(), is(equalTo(updatedUser.getId())));
    }

    @Test
    public void createLegalUserWithMissingMandatoryFields() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
        thrown.expectMessage("LegalPersonType: The LegalPersonType field is required");
        final LegalUser legalUser = randomLegalUser();
        legalUser.setLegalPersonType(null);
        client.getUserService().create(legalUser);
    }

    @Test
    public void createLegalUserWithFieldsContainingInvalidValues() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
        thrown.expectMessage("LegalRepresentativeCountryOfResidence: The requested value is not an ISO 3166-1 alpha-2 code which is expected");
        thrown.expectMessage("Email: The field Email must match the regular expression '([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");
        final LegalUser legalUser = randomLegalUser();
        legalUser.setEmail("contact at acme dot com");
        legalUser.setLegalRepresentativeCountryOfResidence("USA");
        client.getUserService().create(legalUser);
    }

    @Test
    public void getNaturalUserWithInvalidId() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("ressource_not_found: The ressource does not exist");
        thrown.expectMessage("RessourceNotFound: Cannot found the ressource UserNatural with the id=10");
        client.getUserService().getNaturalUser("10");
    }

    @Test
    public void getLegalUserWithInvalidId() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("ressource_not_found: The ressource does not exist");
        thrown.expectMessage("RessourceNotFound: Cannot found the ressource UserLegal with the id=10");
        client.getUserService().getLegalUser("10");
    }

    @Test
    public void getUserWithInvalidId() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("ressource_not_found: The ressource does not exist");
        thrown.expectMessage("RessourceNotFound: Cannot found the ressource User with the id=10");
        client.getUserService().get("10");
    }

    @Test
    public void listUsers() {
        final List<User> users1 = client.getUserService().list(Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
        final List<User> users2 = client.getUserService().list(Sort.by(CREATION_DATE, DESCENDING), Page.of(2));
        assertThat(users1, hasSize(10));
        assertThat(users2, hasSize(10));
        assertThat(users1.get(0).getCreationDate(), is(after(users2.get(9).getCreationDate())));
        assertThat(users1.get(9).getId(), is(greaterThan(users2.get(0).getId())));

        final List<User> users3 = client.getUserService().list(Sort.by(CREATION_DATE, ASCENDING), Page.of(1, 50));
        assertThat(users3, hasSize(50));
        assertThat(users3.get(0).getCreationDate(), is(before(users3.get(49).getCreationDate())));
    }

    @Test
    public void listUserBankAccounts() {
        final Person person = fairy.person();
        final NaturalUser user = client.getUserService().create(randomNaturalUser());
        final BankAccount bankAccount1 = client.getBankAccountService().create(user.getId(), new IbanBankAccount(person.getFullName(), createAddress(person.getAddress()), "FR3020041010124530725S03383", "CRLYFRPP", fairy.textProducer().latinSentence()));
        await().atLeast(TWO_SECONDS);
        final BankAccount bankAccount2 = client.getBankAccountService().create(user.getId(), new BritishBankAccount(person.getFullName(), createAddress(person.getAddress()), "33333334", "070093", fairy.textProducer().latinSentence()));

        final List<BankAccount> bankAccounts = client.getUserService().getBankAccounts(user.getId(), Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
        assertThat(bankAccounts, hasSize(2));
        assertThat(bankAccounts.get(0).getId(), is(equalTo(bankAccount2.getId())));
        assertThat(bankAccounts.get(1).getId(), is(equalTo(bankAccount1.getId())));
    }

    @Test
    public void listUserCards() throws IOException {
        final NaturalUser user = client.getUserService().create(randomNaturalUser());
        final CardRegistration cardRegistration1 = registerCard(user, EUR, "4970100000000154", "1225", "123");
        await().atLeast(TWO_SECONDS);
        final CardRegistration cardRegistration2 = registerCard(user, EUR, "4970100000000155", "1126", "456");

        final List<Card> cards = client.getUserService().getCards(user.getId(), Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
        assertThat(cards, hasSize(2));
        assertThat(cards.get(0).getId(), is(equalTo(cardRegistration2.getCardId())));
        assertThat(cards.get(1).getId(), is(equalTo(cardRegistration1.getCardId())));
    }

    @Test
    public void listUserKycDocument() {
        final NaturalUser user = client.getUserService().create(randomNaturalUser());
        final KycDocument kycDocument1 = client.getKycService().createDocument(user.getId(), new KycDocument(KycDocumentType.ADDRESS_PROOF, null));
        await().atLeast(TWO_SECONDS);
        final KycDocument kycDocument2 = client.getKycService().createDocument(user.getId(), new KycDocument(KycDocumentType.IDENTITY_PROOF, null));

        final List<KycDocument> documents = client.getUserService().getKycDocuments(user.getId(), Filter.none(), Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
        assertThat(documents, hasSize(2));
        assertThat(documents.get(0).getId(), is(equalTo(kycDocument2.getId())));
        assertThat(documents.get(1).getId(), is(equalTo(kycDocument1.getId())));
    }

    @Test
    public void listUserWallets() {
        final NaturalUser user = client.getUserService().create(randomNaturalUser());
        final Wallet wallet1 = client.getWalletService().create(new Wallet(user.getId(), EUR, "EUR", null));
        await().atLeast(TWO_SECONDS);
        final Wallet wallet2 = client.getWalletService().create(new Wallet(user.getId(), USD, "USD", null));

        final List<Wallet> userWallets = client.getUserService().getWallets(user.getId(), Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
        assertThat(userWallets, hasSize(2));
        assertThat(userWallets.get(0).getId(), is(equalTo(wallet2.getId())));
        assertThat(userWallets.get(1).getId(), is(equalTo(wallet1.getId())));
    }

    @Test
    public void listUserTransactions() throws IOException {
        final User user1 = client.getUserService().create(randomNaturalUser());
        final Wallet wallet1 = client.getWalletService().create(new Wallet(user1.getId(), EUR, "wallet", null));
        final String cardId = registerCard(user1, EUR, "4970100000000154", "1225", "123").getCardId();
        final User user2 = client.getUserService().create(randomNaturalUser());
        final Wallet wallet2 = client.getWalletService().create(new Wallet(user2.getId(), EUR, "Wallet to be credited", null));
        final BrowserInfo browserInfo = new BrowserInfo("text/html, application/xhtml+xml, application/xml;q=0.9, /;q=0.8", false, "FR-FR", 4, 1024, 768, 0, "Mozilla/5.0", true);
        final String ipAddress = "8.8.8.8";

        /* final DirectCardPayIn payin = */
        client.getPayInService().createDirectCardPayIn(new DirectCardPayIn(user1.getId(), user1.getId(), wallet1.getId(), cardId, EUR, 4000, 0, null, "https://foo.bar", SecureMode.DEFAULT, ipAddress, browserInfo, null));
        await().atLeast(TWO_SECONDS);
        /* final Transfer transfer = */
        client.getTransferService().create(new Transfer(user1.getId(), wallet1.getId(), wallet2.getId(), EUR, 2000, 0, null));

        final List<Transaction> transactions = client.getUserService().getTransactions(user1.getId(), Filter.none(), Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
        // assertThat(transactions, contains(transfer, payin));
        assertThat(transactions, hasSize(2));
        assertThat(transactions.get(0).getType(), is(equalTo(TransactionType.TRANSFER)));
        assertThat(transactions.get(1).getType(), is(equalTo(TransactionType.PAYIN)));
    }

    private Matcher<NaturalUser> naturalUser(final NaturalUser user, final KycLevel kycLevel, final Instant creationDate) {
        return allOf(asList(
                hasProperty("id", is(notNullValue())),
                hasProperty("email", is(equalTo(user.getEmail()))),
                hasProperty("personType", is(equalTo(PersonType.NATURAL))),
                hasProperty("kycLevel", is(equalTo(kycLevel))),
                hasProperty("creationDate", is(around(creationDate))),
                hasProperty("firstName", is(equalTo(user.getFirstName()))),
                hasProperty("lastName", is(equalTo(user.getLastName()))),
                hasProperty("address", is(equalTo(user.getAddress()))),
                hasProperty("birthday", is(equalTo(user.getBirthday()))),
                hasProperty("nationality", is(equalTo(user.getNationality()))),
                hasProperty("countryOfResidence", is(equalTo(user.getCountryOfResidence()))),
                hasProperty("occupation", is(equalTo(user.getOccupation()))),
                hasProperty("incomeRange", is(equalTo(user.getIncomeRange()))),
                hasProperty("proofOfIdentity", is(nullValue())),
                hasProperty("proofOfAddress", is(nullValue())),
                hasProperty("tag", is(equalTo(user.getTag())))));
    }

    private Matcher<LegalUser> legalUser(final LegalUser legalUser, final KycLevel kycLevel, final Instant creationDate) {
        return allOf(asList(
                hasProperty("id", is(notNullValue())),
                hasProperty("email", is(equalTo(legalUser.getEmail()))),
                hasProperty("personType", is(equalTo(PersonType.LEGAL))),
                hasProperty("kycLevel", is(equalTo(kycLevel))),
                hasProperty("creationDate", is(around(creationDate))),
                hasProperty("name", is(equalTo(legalUser.getName()))),
                hasProperty("legalPersonType", is(equalTo(legalUser.getLegalPersonType()))),
                hasProperty("headquartersAddress", is(equalTo(legalUser.getHeadquartersAddress()))),
                hasProperty("legalRepresentativeFirstName", is(equalTo(legalUser.getLegalRepresentativeFirstName()))),
                hasProperty("legalRepresentativeLastName", is(equalTo(legalUser.getLegalRepresentativeLastName()))),
                hasProperty("legalRepresentativeEmail", is(equalTo(legalUser.getLegalRepresentativeEmail()))),
                hasProperty("legalRepresentativeAddress", is(equalTo(legalUser.getLegalRepresentativeAddress()))),
                hasProperty("legalRepresentativeBirthday", is(equalTo(legalUser.getLegalRepresentativeBirthday()))),
                hasProperty("legalRepresentativeNationality", is(equalTo(legalUser.getLegalRepresentativeNationality()))),
                hasProperty("legalRepresentativeCountryOfResidence", is(equalTo(legalUser.getLegalRepresentativeCountryOfResidence()))),
                hasProperty("statute", is(nullValue())),
                hasProperty("proofOfRegistration", is(nullValue())),
                hasProperty("shareholderDeclaration", is(nullValue())),
                hasProperty("tag", is(equalTo(legalUser.getTag())))));
    }
}
