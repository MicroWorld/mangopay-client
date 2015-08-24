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
import static org.hamcrest.Matchers.nullValue;
import static org.microworld.test.Matchers.around;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.microworld.mangopay.entities.Amount;
import org.microworld.mangopay.entities.DirectCardPayIn;
import org.microworld.mangopay.entities.SecureMode;
import org.microworld.mangopay.entities.TransactionExecutionType;
import org.microworld.mangopay.entities.TransactionNature;
import org.microworld.mangopay.entities.TransactionPaymentType;
import org.microworld.mangopay.entities.TransactionStatus;
import org.microworld.mangopay.entities.TransactionType;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;

public class PayInIT extends AbstractIntegrationTest {
  private static final Currency EUR = Currency.getInstance("EUR");

  @Test
  public void directCardPayIn() throws MalformedURLException, IOException {
    final User user = client.getUserService().create(UserIT.createNaturalUser("foo@bar.com", "Foo", "Bar", "Address", LocalDate.of(1970, 1, 1), "FR", "FR", null, null, null));
    final Wallet wallet = client.getWalletService().create(new Wallet(user.getId(), EUR, "EUR wallet", null));
    final String cardId = registerCard(user, EUR).getCardId();

    final DirectCardPayIn createdDirectCardPayIn = client.getPayInService().createDirectCardPayIn(createDirectCardPayIn(user.getId(), user.getId(), wallet.getId(), cardId, EUR, 4200, 0, SecureMode.DEFAULT, "https://foo.bar", null));
    assertThat(createdDirectCardPayIn, is(directCardPayIn(user.getId(), user.getId(), wallet.getId(), cardId, EUR, 4200, 0, SecureMode.DEFAULT, null, TransactionStatus.SUCCEEDED, null, Instant.now())));
  }

  private DirectCardPayIn createDirectCardPayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final String cardId, final Currency currency, final int debitedAmount, final int feesAmount, final SecureMode secureMode, final String secureModeReturnUrl,
      final String tag) {
    final DirectCardPayIn directCardPayIn = new DirectCardPayIn();
    directCardPayIn.setAuthorId(authorId);
    directCardPayIn.setCreditedUserId(creditedUserId);
    directCardPayIn.setCreditedWalletId(creditedWalletId);
    directCardPayIn.setCardId(cardId);
    directCardPayIn.setDebitedFunds(new Amount(currency, debitedAmount));
    directCardPayIn.setFees(new Amount(currency, feesAmount));
    directCardPayIn.setSecureMode(secureMode);
    directCardPayIn.setSecureModeReturnUrl(secureModeReturnUrl);
    directCardPayIn.setTag(tag);
    return directCardPayIn;
  }

  @SuppressWarnings("unchecked")
  private Matcher<DirectCardPayIn> directCardPayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final String cardId, final Currency currency, final int debitedAmount, final int feesAmount, final SecureMode secureMode, final String secureModeReturnUrl,
      final TransactionStatus status, final String tag, final Instant creationDate) {
    return allOf(
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
        hasProperty("paymentType", is(equalTo(TransactionPaymentType.CARD))),
        hasProperty("executionType", is(equalTo(TransactionExecutionType.DIRECT))),
        hasProperty("tag", is(equalTo(tag))),
        hasProperty("creationDate", is(around(creationDate))));
  }
}
