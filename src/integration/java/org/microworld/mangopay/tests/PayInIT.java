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

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
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
import org.junit.Test;
import org.microworld.mangopay.entities.Address;
import org.microworld.mangopay.entities.Amount;
import org.microworld.mangopay.entities.BankWirePayIn;
import org.microworld.mangopay.entities.DirectCardPayIn;
import org.microworld.mangopay.entities.PayInType;
import org.microworld.mangopay.entities.SecureMode;
import org.microworld.mangopay.entities.TransactionExecutionType;
import org.microworld.mangopay.entities.TransactionNature;
import org.microworld.mangopay.entities.TransactionStatus;
import org.microworld.mangopay.entities.TransactionType;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.entities.bankaccounts.IbanBankAccount;
import org.microworld.mangopay.exceptions.MangopayException;

public class PayInIT extends AbstractIntegrationTest {
  @Test
  public void directCardPayIn() throws MalformedURLException, IOException {
    final User user = client.getUserService().create(randomNaturalUser());
    final Wallet wallet = client.getWalletService().create(new Wallet(user.getId(), EUR, "EUR wallet", null));
    final String cardId = registerCard(user, EUR, "4970100000000154", "1218", "123").getCardId();

    final DirectCardPayIn createdDirectCardPayIn = client.getPayInService().createDirectCardPayIn(new DirectCardPayIn(user.getId(), user.getId(), wallet.getId(), cardId, EUR, 4200, 0, "https://foo.bar", SecureMode.DEFAULT, null));
    assertThat(createdDirectCardPayIn, is(directCardPayIn(user.getId(), user.getId(), wallet.getId(), cardId, EUR, 4200, 0, SecureMode.DEFAULT, null, TransactionStatus.SUCCEEDED, null, Instant.now())));

    final DirectCardPayIn fetchedDirectCardPayIn = (DirectCardPayIn) client.getPayInService().getPayIn(createdDirectCardPayIn.getId());
    assertThat(fetchedDirectCardPayIn, is(equalTo(createdDirectCardPayIn)));
  }

  @Test
  public void bankWirePayIn() {
    final User user = client.getUserService().create(randomNaturalUser());
    final Wallet wallet = client.getWalletService().create(new Wallet(user.getId(), EUR, "EUR wallet", null));

    final BankWirePayIn createdBankWirePayIn = client.getPayInService().createBankWirePayIn(new BankWirePayIn(user.getId(), user.getId(), wallet.getId(), EUR, 1337, 5, null));
    assertThat(createdBankWirePayIn, is(bankWirePayIn(user.getId(), user.getId(), wallet.getId(), EUR, 1337, 5, null, TransactionStatus.CREATED, Instant.now())));

    // final BankWirePayIn fetchedBankWirePayIn = (BankWirePayIn) client.getPayInService().getPayIn(createdBankWirePayIn.getId());
    // assertThat(fetchedBankWirePayIn, is(equalTo(createdBankWirePayIn))); // TODO re-enable when https://github.com/Mangopay/mangopay/issues/11 is fixed.
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

  private Matcher<DirectCardPayIn> directCardPayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final String cardId, final Currency currency, final int debitedAmount, final int feesAmount, final SecureMode secureMode, final String secureModeReturnUrl, final TransactionStatus status, final String tag, final Instant creationDate) {
    return allOf(asList(
        hasProperty("id", is(notNullValue())),
        hasProperty("authorId", is(equalTo(authorId))),
        hasProperty("creditedUserId", is(equalTo(creditedUserId))),
        hasProperty("creditedWalletId", is(equalTo(creditedWalletId))),
        hasProperty("debitedWalletId", is(nullValue())),
        hasProperty("cardId", is(equalTo(cardId))),
        hasProperty("debitedFunds", is(equalTo(new Amount(currency, debitedAmount)))),
        hasProperty("fees", is(equalTo(new Amount(currency, feesAmount)))),
        hasProperty("creditedFunds", is(equalTo(new Amount(currency, debitedAmount - feesAmount)))),
        hasProperty("secureMode", is(equalTo(secureMode))),
        hasProperty("secureModeReturnUrl", is(equalTo(secureModeReturnUrl))),
        hasProperty("status", is(equalTo(status))),
        hasProperty("type", is(equalTo(TransactionType.PAYIN))),
        hasProperty("nature", is(equalTo(TransactionNature.REGULAR))),
        hasProperty("paymentType", is(equalTo(PayInType.CARD))),
        hasProperty("executionType", is(equalTo(TransactionExecutionType.DIRECT))),
        hasProperty("tag", is(equalTo(tag))),
        hasProperty("creationDate", is(around(creationDate)))));
  }

  private Matcher<BankWirePayIn> bankWirePayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final Currency currency, final int declaredDebitedAmount, final int declaredFeesAmount, final String tag, final TransactionStatus status, final Instant creationDate) {
    return allOf(asList(
        hasProperty("id", is(notNullValue())),
        hasProperty("authorId", is(equalTo(authorId))),
        hasProperty("creditedUserId", is(equalTo(creditedUserId))),
        hasProperty("creditedWalletId", is(equalTo(creditedWalletId))),
        hasProperty("declaredDebitedFunds", is(equalTo(new Amount(currency, declaredDebitedAmount)))),
        hasProperty("declaredFees", is(equalTo(new Amount(currency, declaredFeesAmount)))),
        hasProperty("debitedWalletId", is(nullValue())),
        hasProperty("debitedFunds", is(nullValue())),
        hasProperty("creditedFunds", is(nullValue())),
        hasProperty("fees", is(nullValue())),
        hasProperty("wireReference", is(notNullValue())),
        hasProperty("bankAccount", is(equalTo(new IbanBankAccount("MANGOPAY", new Address("1 Mango Street", null, "Paris", null, "75010", "FR"), "FR7618829754160173622224251", "CMBRFR2BCME", null)))),
        hasProperty("resultCode", is(nullValue())),
        hasProperty("resultMessage", is(nullValue())),
        hasProperty("executionDate", is(nullValue())),
        hasProperty("type", is(equalTo(TransactionType.PAYIN))),
        hasProperty("nature", is(equalTo(TransactionNature.REGULAR))),
        hasProperty("paymentType", is(equalTo(PayInType.BANK_WIRE))),
        hasProperty("executionType", is(equalTo(TransactionExecutionType.DIRECT))),
        hasProperty("tag", is(equalTo(tag))),
        hasProperty("status", is(equalTo(status))),
        hasProperty("creationDate", is(around(creationDate)))));
  }
}
