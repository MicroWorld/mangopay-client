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
import static org.microworld.mangopay.SortDirection.ASCENDING;
import static org.microworld.mangopay.SortDirection.DESCENDING;
import static org.microworld.mangopay.SortField.CREATION_DATE;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Before;
import org.junit.Test;
import org.microworld.mangopay.Page;
import org.microworld.mangopay.Sort;
import org.microworld.mangopay.UserApi;
import org.microworld.mangopay.entities.IncomeRange;
import org.microworld.mangopay.entities.KycLevel;
import org.microworld.mangopay.entities.LegalPersonType;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.PersonType;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.exceptions.MangoPayException;

public class UserIT extends AbstractIntegrationTest {
  private UserApi userApi;

  @Before
  public void setUpUserApi() {
    userApi = UserApi.createDefault(connection);
  }

  @Test
  public void createGetUpdateNaturalUser() {
    final NaturalUser createdUser = userApi.create(createNaturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, null));
    assertThat(createdUser, is(naturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, null, PersonType.NATURAL, KycLevel.LIGHT, Instant.now())));

    final NaturalUser fetchedUser = userApi.getNaturalUser(createdUser.getId());
    assertThat(fetchedUser, is(naturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, null, PersonType.NATURAL, KycLevel.LIGHT, Instant.now())));
    assertThat(fetchedUser.getId(), is(equalTo(createdUser.getId())));

    fetchedUser.setTag("Good user");
    final NaturalUser updatedUser = userApi.update(fetchedUser);
    assertThat(updatedUser, is(naturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, "Good user", PersonType.NATURAL, KycLevel.LIGHT, Instant.now())));
    assertThat(updatedUser.getId(), is(equalTo(fetchedUser.getId())));

    final User user = userApi.get(updatedUser.getId());
    assertThat((NaturalUser) user, is(naturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, IncomeRange.BETWEEN_30_AND_50k€, "Good user", PersonType.NATURAL, KycLevel.LIGHT, Instant.now())));
    assertThat(user.getId(), is(equalTo(updatedUser.getId())));
  }

  @Test
  public void createNaturalUserWithMissingMandatoryFields() {
    thrown.expect(MangoPayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Email: The Email field is required.");
    thrown.expectMessage("Birthday: The Birthday field is required.");
    userApi.create(createNaturalUser(null, "John", "Doe", "John's Address", null, "US", "US", null, null, null));
  }

  @Test
  public void createNaturalUserWithFieldsContainingInvalidValues() {
    thrown.expect(MangoPayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Nationality: Error converting value \"USA\" to type");
    userApi.create(createNaturalUser("john@doe.com", "John", "Doe", "John's Address", LocalDate.of(1942, 11, 13), "USA", "US", null, null, null));
  }

  @Test
  public void createGetUpdateLegalUser() {
    final LegalUser createdUser = userApi.create(createLegalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null));
    assertThat(createdUser, is(legalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, PersonType.LEGAL, KycLevel.LIGHT, Instant.now())));

    final LegalUser fetchedUser = userApi.getLegalUser(createdUser.getId());
    assertThat(fetchedUser, is(legalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null, PersonType.LEGAL, KycLevel.LIGHT, Instant.now())));
    assertThat(fetchedUser.getId(), is(equalTo(createdUser.getId())));

    fetchedUser.setTag("Good user");
    final LegalUser updatedUser = userApi.update(fetchedUser);
    assertThat(updatedUser, is(legalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", "Good user", PersonType.LEGAL, KycLevel.LIGHT, Instant.now())));
    assertThat(updatedUser.getId(), is(equalTo(fetchedUser.getId())));

    final User user = userApi.get(updatedUser.getId());
    assertThat((LegalUser) user, is(legalUser("contact@acme.com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", "Good user", PersonType.LEGAL, KycLevel.LIGHT, Instant.now())));
    assertThat(user.getId(), is(equalTo(updatedUser.getId())));
  }

  @Test
  public void createLegalUserWithMissingMandatoryFields() {
    thrown.expect(MangoPayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("LegalPersonType: The LegalPersonType field is required.");
    userApi.create(createLegalUser("contact@acme.com", "ACME", null, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "US", null));
  }

  @Test
  public void createLegalUserWithFieldsContainingInvalidValues() {
    thrown.expect(MangoPayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("LegalRepresentativeCountryOfResidence: Error converting value \"USA\" to type");
    thrown.expectMessage("Email: The field Email must match the regular expression '([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");
    userApi.create(createLegalUser("contact at acme dot com", "ACME", LegalPersonType.BUSINESS, "ACME Address", "John", "Doe", "john@doe.com", "John's Address", LocalDate.of(1942, 11, 13), "US", "USA", null));
  }

  @Test
  public void listUsers() {
    final List<User> users1 = userApi.list(Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
    final List<User> users2 = userApi.list(Sort.by(CREATION_DATE, DESCENDING), Page.of(2));
    assertThat(users1.get(0).getCreationDate(), is(after(users2.get(9).getCreationDate())));
    assertThat(users1.get(9).getId(), is(greaterThan(users2.get(0).getId())));
    assertThat(users1, hasSize(10));
    assertThat(users2, hasSize(10));

    final List<User> users3 = userApi.list(Sort.by(CREATION_DATE, ASCENDING), Page.of(1, 50));
    assertThat(users3.get(0).getCreationDate(), is(before(users3.get(49).getCreationDate())));
    assertThat(users3, hasSize(50));
  }

  private NaturalUser createNaturalUser(final String email, final String firstName, final String lastName, final String address, final LocalDate birthday, final String nationality, final String countryOfResidence, final String occupation, final IncomeRange incomeRange, final String tag) {
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

  private LegalUser createLegalUser(final String email, final String name, final LegalPersonType legalPersonType, final String headquartersAddress, final String legalRepresentativeFirstName, final String legalRepresentativeLastName, final String legalRepresentativeEmail,
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

  private Matcher<Instant> around(final Instant instant) {
    return new TypeSafeDiagnosingMatcher<Instant>() {
      @Override
      public void describeTo(final Description description) {
        description.appendValue(instant);
      }

      @Override
      protected boolean matchesSafely(final Instant item, final Description mismatchDescription) {
        if (instant.minusSeconds(5).isAfter(item) || instant.plusSeconds(5).isBefore(item)) {
          mismatchDescription.appendText("was ").appendValue(item);
          return false;
        }
        return true;
      }
    };
  }

  private Matcher<Instant> before(final Instant instant) {
    return new TypeSafeDiagnosingMatcher<Instant>() {
      @Override
      public void describeTo(final Description description) {
        description.appendText("before ").appendValue(instant);
      }

      @Override
      protected boolean matchesSafely(final Instant item, final Description mismatchDescription) {
        if (instant.isBefore(item)) {
          mismatchDescription.appendText("was ").appendValue(item);
          return false;
        }
        return true;
      }
    };
  }

  private Matcher<Instant> after(final Instant instant) {
    return new TypeSafeDiagnosingMatcher<Instant>() {
      @Override
      public void describeTo(final Description description) {
        description.appendText("after ").appendValue(instant);
      }

      @Override
      protected boolean matchesSafely(final Instant item, final Description mismatchDescription) {
        if (instant.isAfter(item)) {
          mismatchDescription.appendText("was ").appendValue(item);
          return false;
        }
        return true;
      }
    };
  }
}
