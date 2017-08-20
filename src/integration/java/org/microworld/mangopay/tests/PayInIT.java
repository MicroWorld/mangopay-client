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

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.microworld.test.Matchers.around;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Currency;

import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;
import org.microworld.mangopay.entities.Address;
import org.microworld.mangopay.entities.Amount;
import org.microworld.mangopay.entities.BankWirePayIn;
import org.microworld.mangopay.entities.CardType;
import org.microworld.mangopay.entities.CultureCode;
import org.microworld.mangopay.entities.DirectCardPayIn;
import org.microworld.mangopay.entities.ExecutionType;
import org.microworld.mangopay.entities.PayInRefundParameters;
import org.microworld.mangopay.entities.PayInType;
import org.microworld.mangopay.entities.SecureMode;
import org.microworld.mangopay.entities.TransactionNature;
import org.microworld.mangopay.entities.TransactionStatus;
import org.microworld.mangopay.entities.TransactionType;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.entities.WebCardPayIn;
import org.microworld.mangopay.entities.bankaccounts.IbanBankAccount;
import org.microworld.mangopay.exceptions.MangopayException;

public class PayInIT extends AbstractIntegrationTest {
  @Test
  public void bankWirePayIn() {
    final User user = client.getUserService().create(randomNaturalUser());
    final Wallet wallet = client.getWalletService().create(new Wallet(user.getId(), EUR, "EUR wallet", null));

    final BankWirePayIn createdPayIn = client.getPayInService().createBankWirePayIn(new BankWirePayIn(user.getId(), user.getId(), wallet.getId(), EUR, 1337, 5, null));
    assertThat(createdPayIn, is(bankWirePayIn(user.getId(), user.getId(), wallet.getId(), EUR, 1337, 5, null, TransactionStatus.CREATED, Instant.now())));

    final BankWirePayIn fetchedPayIn = (BankWirePayIn) client.getPayInService().getPayIn(createdPayIn.getId());
    assertThat(fetchedPayIn, is(equalTo(createdPayIn)));
  }

  @Test
  public void directCardPayIn() throws MalformedURLException, IOException {
    final User user = client.getUserService().create(randomNaturalUser());
    final Wallet wallet = client.getWalletService().create(new Wallet(user.getId(), EUR, "EUR wallet", null));
    final String cardId = registerCard(user, EUR, "4970100000000154", "1218", "123").getCardId();

    final DirectCardPayIn createdPayIn = client.getPayInService().createDirectCardPayIn(new DirectCardPayIn(user.getId(), user.getId(), wallet.getId(), cardId, EUR, 4200, 0, null, "https://foo.bar", SecureMode.DEFAULT, null));
    assertThat(createdPayIn, is(directCardPayIn(user.getId(), user.getId(), wallet.getId(), cardId, EUR, 4200, 0, null, SecureMode.DEFAULT, null, null, TransactionStatus.SUCCEEDED, null, Instant.now(), Instant.now(), "000000", "Success")));

    final DirectCardPayIn fetchedPayIn = (DirectCardPayIn) client.getPayInService().getPayIn(createdPayIn.getId());
    assertThat(fetchedPayIn, is(equalTo(createdPayIn)));

    assertThat(client.getWalletService().get(wallet.getId()).getBalance().getCents(), is(equalTo(4200)));
    client.getPayInService().refund(createdPayIn.getId(), new PayInRefundParameters(user.getId(), null, null, null));
    assertThat(client.getWalletService().get(wallet.getId()).getBalance().getCents(), is(equalTo(0)));
  }

  @Test
  public void webCardPayIn() {
    final User user = client.getUserService().create(randomNaturalUser());
    final Wallet wallet = client.getWalletService().create(new Wallet(user.getId(), EUR, "EUR wallet", null));

    final WebCardPayIn createdPayIn = client.getPayInService().createWebCardPayIn(new WebCardPayIn(user.getId(), user.getId(), wallet.getId(), CardType.CB_VISA_MASTERCARD, EUR, 4200, 0, CultureCode.FR, null, "https://my.site.com", null, SecureMode.DEFAULT, null));
    assertThat(createdPayIn, is(webCardPayIn(user.getId(), user.getId(), wallet.getId(), CardType.CB_VISA_MASTERCARD, EUR, 4200, 0, CultureCode.FR, null, SecureMode.DEFAULT, "https://my.site.com/?transactionId=" + createdPayIn.getId(), TransactionStatus.CREATED, null, Instant.now(), null, null)));

    final WebCardPayIn fetchedPayIn = (WebCardPayIn) client.getPayInService().getPayIn(createdPayIn.getId());
    assertThat(fetchedPayIn, is(equalTo(createdPayIn)));
  }

  @Test
  public void getPayInWithInvalidId() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("ressource_not_found: The ressource does not exist");
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource PayIn with the id=10");
    client.getPayInService().getPayIn("10");
  }

  @Test
  public void getPayInWithNullId() {
    thrown.expect(NullPointerException.class);
    client.getPayInService().getPayIn(null);
  }

  @Test
  @Ignore
  public void refundPayInWithInvalidId() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("ressource_not_found: The ressource does not exist");
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource Transfer with the id=10");
    client.getPayInService().refund("10", new PayInRefundParameters("11", null, null, null));
  }

  private Matcher<BankWirePayIn> bankWirePayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final Currency currency, final int declaredDebitedAmount, final int declaredFeesAmount, final String tag, final TransactionStatus status, final Instant creationDate) {
    return allOf(asList(
        // Entity
        hasProperty("id", is(notNullValue())),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("tag", is(equalTo(tag))),
        // Transaction
        hasProperty("authorId", is(equalTo(authorId))),
        hasProperty("creditedUserId", is(equalTo(creditedUserId))),
        hasProperty("debitedFunds", is(nullValue())),
        hasProperty("creditedFunds", is(nullValue())),
        hasProperty("fees", is(nullValue())),
        hasProperty("status", is(equalTo(status))),
        hasProperty("resultCode", is(nullValue())),
        hasProperty("resultMessage", is(nullValue())),
        hasProperty("executionDate", is(nullValue())),
        hasProperty("type", is(equalTo(TransactionType.PAYIN))),
        hasProperty("nature", is(equalTo(TransactionNature.REGULAR))),
        // PayIn
        hasProperty("creditedWalletId", is(equalTo(creditedWalletId))),
        hasProperty("debitedWalletId", is(nullValue())),
        hasProperty("paymentType", is(equalTo(PayInType.BANK_WIRE))),
        hasProperty("executionType", is(equalTo(ExecutionType.DIRECT))),
        // BankWirePayIn
        hasProperty("declaredDebitedFunds", is(equalTo(new Amount(currency, declaredDebitedAmount)))),
        hasProperty("declaredFees", is(equalTo(new Amount(currency, declaredFeesAmount)))),
        hasProperty("wireReference", is(notNullValue())),
        hasProperty("bankAccount", is(equalTo(new IbanBankAccount("MANGOPAY", new Address("1 Mango Street", null, "Paris", null, "75010", "FR"), "FR7618829754160173622224251", "CMBRFR2BCME", null))))));
  }

  private Matcher<DirectCardPayIn> directCardPayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final String cardId, final Currency currency, final int debitedAmount, final int feesAmount, final String statementDescriptor, final SecureMode secureMode, final String secureModeReturnUrl, final String secureModeRedirectUrl, final TransactionStatus status, final String tag, final Instant creationDate, final Instant executionDate, final String resultCode, final String resultMessage) {
    return allOf(asList(
        // Entity
        hasProperty("id", is(notNullValue())),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("tag", is(equalTo(tag))),
        // Transaction
        hasProperty("authorId", is(equalTo(authorId))),
        hasProperty("creditedUserId", is(equalTo(creditedUserId))),
        hasProperty("debitedFunds", is(equalTo(new Amount(currency, debitedAmount)))),
        hasProperty("creditedFunds", is(equalTo(new Amount(currency, debitedAmount - feesAmount)))),
        hasProperty("fees", is(equalTo(new Amount(currency, feesAmount)))),
        hasProperty("status", is(equalTo(status))),
        hasProperty("resultCode", is(equalTo(resultCode))),
        hasProperty("resultMessage", is(equalTo(resultMessage))),
        hasProperty("executionDate", is(around(executionDate))),
        hasProperty("type", is(equalTo(TransactionType.PAYIN))),
        hasProperty("nature", is(equalTo(TransactionNature.REGULAR))),
        // PayIn
        hasProperty("creditedWalletId", is(equalTo(creditedWalletId))),
        hasProperty("debitedWalletId", is(nullValue())),
        hasProperty("paymentType", is(equalTo(PayInType.CARD))),
        hasProperty("executionType", is(equalTo(ExecutionType.DIRECT))),
        // CardPayIn
        hasProperty("secureMode", is(equalTo(secureMode))),
        hasProperty("statementDescriptor", is(equalTo(statementDescriptor))),
        // DirectCardPayIn
        hasProperty("cardId", is(equalTo(cardId))),
        hasProperty("secureModeReturnUrl", is(equalTo(secureModeReturnUrl))),
        hasProperty("secureModeRedirectUrl", is(equalTo(secureModeRedirectUrl)))));
  }

  private Matcher<WebCardPayIn> webCardPayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final CardType cardType, final Currency currency, final int debitedAmount, final int feesAmount, final CultureCode cultureCode, final String statementDescriptor, final SecureMode secureMode, final String returnUrl, final TransactionStatus status, final String tag, final Instant creationDate, final String resultCode, final String resultMessage) {
    return allOf(asList(
        // Entity
        hasProperty("id", is(notNullValue())),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("tag", is(equalTo(tag))),
        // Transaction
        hasProperty("authorId", is(equalTo(authorId))),
        hasProperty("creditedUserId", is(equalTo(creditedUserId))),
        hasProperty("debitedFunds", is(equalTo(new Amount(currency, debitedAmount)))),
        hasProperty("creditedFunds", is(equalTo(new Amount(currency, debitedAmount - feesAmount)))),
        hasProperty("fees", is(equalTo(new Amount(currency, feesAmount)))),
        hasProperty("status", is(equalTo(status))),
        hasProperty("resultCode", is(equalTo(resultCode))),
        hasProperty("resultMessage", is(equalTo(resultMessage))),
        hasProperty("executionDate", is(nullValue())),
        hasProperty("type", is(equalTo(TransactionType.PAYIN))),
        hasProperty("nature", is(equalTo(TransactionNature.REGULAR))),
        // PayIn
        hasProperty("creditedWalletId", is(equalTo(creditedWalletId))),
        hasProperty("debitedWalletId", is(nullValue())),
        hasProperty("paymentType", is(equalTo(PayInType.CARD))),
        hasProperty("executionType", is(equalTo(ExecutionType.WEB))),
        // CardPayIn
        hasProperty("secureMode", is(equalTo(secureMode))),
        hasProperty("statementDescriptor", is(equalTo(statementDescriptor))),
        // WebCardPayIn
        hasProperty("cultureCode", is(equalTo(cultureCode))),
        hasProperty("cardType", is(equalTo(cardType))),
        hasProperty("returnUrl", is(equalTo(returnUrl))),
        hasProperty("redirectUrl", is(notNullValue())),
        hasProperty("templateUrlOptions", is(anEmptyMap()))));
  }
}
