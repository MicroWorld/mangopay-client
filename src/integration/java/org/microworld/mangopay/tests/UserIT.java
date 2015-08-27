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

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.microworld.mangopay.entities.Card;
import org.microworld.mangopay.entities.CardRegistration;
import org.microworld.mangopay.entities.IncomeRange;
import org.microworld.mangopay.entities.KycLevel;
import org.microworld.mangopay.entities.LegalPersonType;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.PersonType;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.exceptions.MangopayException;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public class UserIT extends AbstractIntegrationTest {
  private static final Currency EUR = Currency.getInstance("EUR");
  private static final Currency USD = Currency.getInstance("USD");

  @Test
  public void createGetUpdateNaturalUser() {
    final Instant creationDate = Instant.now();
    final NaturalUser createdUser = client.getUserService().create(createNaturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, null));
    assertThat(createdUser, is(naturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, null, PersonType.NATURAL, KycLevel.LIGHT, creationDate)));

    final NaturalUser fetchedUser = client.getUserService().getNaturalUser(createdUser.getId());
    assertThat(fetchedUser, is(naturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, null, PersonType.NATURAL, KycLevel.LIGHT, creationDate)));
    assertThat(fetchedUser.getId(), is(equalTo(createdUser.getId())));

    fetchedUser.setTag("Good user");
    final NaturalUser updatedUser = client.getUserService().update(fetchedUser);
    assertThat(updatedUser, is(naturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, "Good user", PersonType.NATURAL, KycLevel.LIGHT, creationDate)));
    assertThat(updatedUser.getId(), is(equalTo(fetchedUser.getId())));

    final User user = client.getUserService().get(updatedUser.getId());
    assertThat((NaturalUser) user, is(naturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, "Good user", PersonType.NATURAL, KycLevel.LIGHT, creationDate)));
    assertThat(user.getId(), is(equalTo(updatedUser.getId())));
  }

  @Test
  public void createNaturalUserWithMissingMandatoryFields() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Email: The Email field is required.");
    thrown.expectMessage("Birthday: The Birthday field is required.");
    client.getUserService().create(createNaturalUser(null, "John", "Doe", "John's Address", null, "US", "US", null, null, null));
  }

  @Test
  public void createNaturalUserWithFieldsContainingInvalidValues() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Nationality: Error converting value \"USA\" to type");
    client.getUserService().create(createNaturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "USA", "US", null, null, null));
  }

  @Test
  public void createGetUpdateLegalUser() {
    final Instant creationDate = Instant.now();
    final LegalUser createdUser = client.getUserService().create(createLegalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null));
    assertThat(createdUser, is(legalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, PersonType.LEGAL, KycLevel.LIGHT, creationDate)));

    final LegalUser fetchedUser = client.getUserService().getLegalUser(createdUser.getId());
    assertThat(fetchedUser, is(legalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, PersonType.LEGAL, KycLevel.LIGHT, creationDate)));
    assertThat(fetchedUser.getId(), is(equalTo(createdUser.getId())));

    fetchedUser.setTag("Good user");
    final LegalUser updatedUser = client.getUserService().update(fetchedUser);
    assertThat(updatedUser, is(legalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", "Good user", PersonType.LEGAL, KycLevel.LIGHT, creationDate)));
    assertThat(updatedUser.getId(), is(equalTo(fetchedUser.getId())));

    final User user = client.getUserService().get(updatedUser.getId());
    assertThat((LegalUser) user, is(legalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", "Good user", PersonType.LEGAL, KycLevel.LIGHT, creationDate)));
    assertThat(user.getId(), is(equalTo(updatedUser.getId())));
  }

  @Test
  public void createLegalUserWithMissingMandatoryFields() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("LegalPersonType: The LegalPersonType field is required.");
    client.getUserService().create(createLegalUser("contact@acme.com", "ACME", null, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null));
  }

  @Test
  public void createLegalUserWithFieldsContainingInvalidValues() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("LegalRepresentativeCountryOfResidence: Error converting value \"USA\" to type");
    thrown.expectMessage("Email: The field Email must match the regular expression '([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");
    client.getUserService().create(createLegalUser("contact at acme dot com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "USA", null));
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
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource UserLegal with the id=10"); // Misleading error message.
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
  public void listUserWallets() throws InterruptedException {
    final NaturalUser user = client.getUserService().create(createNaturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, null));
    final Wallet wallet1 = client.getWalletService().create(new Wallet(user.getId(), EUR, "EUR", null));
    Thread.sleep(2000);
    final Wallet wallet2 = client.getWalletService().create(new Wallet(user.getId(), USD, "USD", null));

    final List<Wallet> userWallets = client.getUserService().getWallets(user.getId(), Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
    assertThat(userWallets, hasSize(2));
    assertThat(userWallets.get(0).getId(), is(equalTo(wallet2.getId())));
    assertThat(userWallets.get(1).getId(), is(equalTo(wallet1.getId())));
  }

  @Test
  public void listUserCards() throws MalformedURLException, IOException, InterruptedException {
    final NaturalUser user = client.getUserService().create(createNaturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, null));
    final CardRegistration cardRegistration1 = registerCard(user, EUR, "4970100000000154", "1218", "123");
    Thread.sleep(2000);
    final CardRegistration cardRegistration2 = registerCard(user, USD, "4970100000000155", "1119", "456");

    final List<Card> cards = client.getUserService().getCards(user.getId(), Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
    assertThat(cards, hasSize(2));
    assertThat(cards.get(0).getId(), is(equalTo(cardRegistration2.getCardId())));
    assertThat(cards.get(1).getId(), is(equalTo(cardRegistration1.getCardId())));
  }

  public static NaturalUser createNaturalUser(final String email, final String firstName, final String lastName, final String address, final LocalDate birthday, final String nationality, final String countryOfResidence, final String occupation, final IncomeRange incomeRange, final String tag) {
    final NaturalUser user = new NaturalUser();
    user.setEmail(email);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setAddress(address);
    user.setBirthday(birthday);
    user.setNationality(nationality);
    user.setCountryOfResidence(countryOfResidence);
    user.setOccupation(occupation);
    user.setIncomeRange(incomeRange);
    user.setTag(tag);
    return user;
  }

  @SuppressWarnings("unchecked")
  private Matcher<NaturalUser> naturalUser(final String email, final String firstName, final String lastName, final String address, final LocalDate birthday, final String nationality, final String countryOfResidence, final String occupation, final IncomeRange incomeRange, final String tag,
      final PersonType personType, final KycLevel kycLevel, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("email", is(equalTo(email))),
        hasProperty("personType", is(equalTo(personType))),
        hasProperty("kycLevel", is(equalTo(kycLevel))),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("firstName", is(equalTo(firstName))),
        hasProperty("lastName", is(equalTo(lastName))),
        hasProperty("address", is(equalTo(address))),
        hasProperty("birthday", is(equalTo(birthday))),
        hasProperty("nationality", is(equalTo(nationality))),
        hasProperty("countryOfResidence", is(equalTo(countryOfResidence))),
        hasProperty("occupation", is(equalTo(occupation))),
        hasProperty("incomeRange", is(equalTo(incomeRange))),
        hasProperty("proofOfIdentity", is(nullValue())),
        hasProperty("proofOfAddress", is(nullValue())),
        hasProperty("tag", is(equalTo(tag))));
  }

  public static LegalUser createLegalUser(final String email, final String name, final LegalPersonType legalPersonType, final String headquartersAddress, final String legalRepresentativeFirstName, final String legalRepresentativeLastName, final String legalRepresentativeEmail,
      final String legalRepresentativeAddress, final LocalDate legalRepresentativeBirthday, final String legalRepresentativeNationality, final String legalRepresentativeCountryOfResidence, final String tag) {
    final LegalUser user = new LegalUser();
    user.setEmail(email);
    user.setName(name);
    user.setLegalPersonType(legalPersonType);
    user.setHeadquartersAddress(headquartersAddress);
    user.setLegalRepresentativeFirstName(legalRepresentativeFirstName);
    user.setLegalRepresentativeLastName(legalRepresentativeLastName);
    user.setLegalRepresentativeEmail(legalRepresentativeEmail);
    user.setLegalRepresentativeAddress(legalRepresentativeAddress);
    user.setLegalRepresentativeBirthday(legalRepresentativeBirthday);
    user.setLegalRepresentativeNationality(legalRepresentativeNationality);
    user.setLegalRepresentativeCountryOfResidence(legalRepresentativeCountryOfResidence);
    user.setTag(tag);
    return user;
  }

  @SuppressWarnings("unchecked")
  private Matcher<LegalUser> legalUser(final String email, final String name, final LegalPersonType legalPersonType, final String headquartersAddress, final String legalRepresentativeFirstName, final String legalRepresentativeLastName, final String legalRepresentativeEmail,
      final String legalRepresentativeAddress, final LocalDate legalRepresentativeBirthday, final String legalRepresentativeNationality, final String legalRepresentativeCountryOfResidence, final String tag, final PersonType personType, final KycLevel kycLevel, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("email", is(equalTo(email))),
        hasProperty("personType", is(equalTo(personType))),
        hasProperty("kycLevel", is(equalTo(kycLevel))),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("name", is(equalTo(name))),
        hasProperty("legalPersonType", is(equalTo(legalPersonType))),
        hasProperty("headquartersAddress", is(equalTo(headquartersAddress))),
        hasProperty("legalRepresentativeFirstName", is(equalTo(legalRepresentativeFirstName))),
        hasProperty("legalRepresentativeLastName", is(equalTo(legalRepresentativeLastName))),
        hasProperty("legalRepresentativeEmail", is(equalTo(legalRepresentativeEmail))),
        hasProperty("legalRepresentativeAddress", is(equalTo(legalRepresentativeAddress))),
        hasProperty("legalRepresentativeBirthday", is(equalTo(legalRepresentativeBirthday))),
        hasProperty("legalRepresentativeNationality", is(equalTo(legalRepresentativeNationality))),
        hasProperty("legalRepresentativeCountryOfResidence", is(equalTo(legalRepresentativeCountryOfResidence))),
        hasProperty("statute", is(nullValue())),
        hasProperty("proofOfRegistration", is(nullValue())),
        hasProperty("shareholderDeclaration", is(nullValue())),
        hasProperty("tag", is(equalTo(tag))));
  }
}
