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

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.company.Company;
import io.codearte.jfairy.producer.person.Person;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.microworld.mangopay.MangopayClient;
import org.microworld.mangopay.TestEnvironment;
import org.microworld.mangopay.entities.Address;
import org.microworld.mangopay.entities.BrowserInfo;
import org.microworld.mangopay.entities.CardRegistration;
import org.microworld.mangopay.entities.DirectCardPayIn;
import org.microworld.mangopay.entities.IncomeRange;
import org.microworld.mangopay.entities.LegalPersonType;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.SecureMode;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.microworld.mangopay.misc.Predicates.not;

public abstract class AbstractIntegrationTest {
    protected static final Currency EUR = Currency.getInstance("EUR");
    protected static final Currency USD = Currency.getInstance("USD");
    protected static final Currency XAF = Currency.getInstance("XAF");

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    protected MangopayClient client;
    protected Fairy fairy;

    private static String getContent(final InputStream inputStream) throws IOException {
        final StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }
        }
        return content.deleteCharAt(content.length() - 1).toString();
    }

    @Before
    public void setUpTestEnvironment() {
        client = MangopayClient.createDefault(TestEnvironment.getInstance().getConnection());
        fairy = Fairy.create();
    }

    protected NaturalUser randomNaturalUser() {
        final Person person = fairy.person();

        final NaturalUser user = new NaturalUser();
        user.setEmail(person.getEmail());
        user.setFirstName(person.getFirstName());
        user.setLastName(person.getLastName());
        user.setAddress(createAddress(person.getAddress()));
        user.setBirthday(LocalDate.parse(person.getDateOfBirth().toString("yyyy-MM-dd")));
        user.setNationality(randomCountry());
        user.setCountryOfResidence(randomCountry());
        user.setOccupation(fairy.textProducer().latinSentence());
        user.setIncomeRange(IncomeRange.values()[fairy.baseProducer().randomBetween(0, IncomeRange.values().length - 1)]);
        user.setTag(fairy.textProducer().latinSentence());
        return user;
    }

    protected LegalUser randomLegalUser() {
        final Person person = fairy.person();
        final Company company = fairy.company();

        final LegalUser user = new LegalUser();
        user.setEmail(company.getEmail());
        user.setName(company.getName());
        user.setLegalPersonType(LegalPersonType.values()[fairy.baseProducer().randomBetween(0, LegalPersonType.values().length - 1)]);
        user.setHeadquartersAddress(createAddress(fairy.person().getAddress()));
        user.setLegalRepresentativeFirstName(person.getFirstName());
        user.setLegalRepresentativeLastName(person.getLastName());
        user.setLegalRepresentativeEmail(person.getEmail());
        user.setLegalRepresentativeAddress(createAddress(person.getAddress()));
        user.setLegalRepresentativeBirthday(LocalDate.parse(person.getDateOfBirth().toString("yyyy-MM-dd")));
        user.setLegalRepresentativeNationality(randomCountry());
        user.setLegalRepresentativeCountryOfResidence(randomCountry());
        user.setTag(fairy.textProducer().latinSentence());
        return user;
    }

    protected User getUserWithMoney(final int cents, final Currency currency) throws IOException {
        final User user = client.getUserService().create(randomNaturalUser());
        final Wallet wallet = client.getWalletService().create(new Wallet(user.getId(), currency, "wallet", null));
        final String cardId = registerCard(user, currency, "4970105191923460", "1225", "123").getCardId();
        final BrowserInfo browserInfo = new BrowserInfo("text/html, application/xhtml+xml, application/xml;q=0.9, /;q=0.8", false, "FR-FR", 4, 1024, 768, 0, "Mozilla/5.0", true);
        final String ipAddress = "8.8.8.8";
        client.getPayInService().createDirectCardPayIn(new DirectCardPayIn(user.getId(), user.getId(), wallet.getId(), cardId, currency, cents, 0, null, "https://foo.bar", SecureMode.DEFAULT, ipAddress, browserInfo, null));
        return user;
    }

    protected String randomCountry() {
        List<String> blockedCountries = asList("AF", "BA", "BS", "BV", "BW", "ET", "GH", "GN", "GS", "GY", "HM", "IQ", "IR", "KH", "KP", "LA", "LK", "MA", "MN", "MU", "PK", "RS", "SS", "SY", "TN", "TT", "UG", "VU", "YE");
        List<String> countryCodes = Arrays.stream(Locale.getISOCountries()).filter(not(blockedCountries::contains)).collect(toList());
        return countryCodes.get(fairy.baseProducer().randomBetween(0, countryCodes.size() - 1));
    }

    protected Address createAddress(final io.codearte.jfairy.producer.person.Address fairyAddress) {
        return new Address(fairyAddress.getStreetNumber() + ", " + fairyAddress.getStreet(), fairyAddress.getApartmentNumber(), fairyAddress.getCity(), fairy.textProducer().latinWord(), fairyAddress.getPostalCode(), randomCountry());
    }

    protected CardRegistration registerCard(final User user, final Currency currency, final String cardNumber, final String cardExpirationDate, final String cardCvx) throws IOException {
        CardRegistration cardRegistration = client.getCardRegistrationService().create(new CardRegistration(user.getId(), currency));
        cardRegistration.setRegistrationData(getRegistrationData(cardRegistration.getCardRegistrationUrl(), cardRegistration.getPreregistrationData(), cardRegistration.getAccessKey(), cardNumber, cardExpirationDate, cardCvx));
        cardRegistration = client.getCardRegistrationService().update(cardRegistration);
        return cardRegistration;
    }

    protected String getRegistrationData(final String cardRegistrationUrl, final String preregistrationData, final String accessKey, final String cardNumber, final String cardExpirationDate, final String cardCvx) throws IOException {
        final HttpsURLConnection connection = (HttpsURLConnection) new URL(cardRegistrationUrl).openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");

        try (OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream(), UTF_8)) {
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
            case HTTP_OK:
                return getContent(connection.getInputStream());
            default:
                throw new RuntimeException(getContent(connection.getErrorStream()));
        }
    }
}
