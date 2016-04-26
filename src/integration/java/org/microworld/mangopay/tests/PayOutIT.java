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
import org.microworld.mangopay.entities.Amount;
import org.microworld.mangopay.entities.BankWirePayOut;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.TransactionNature;
import org.microworld.mangopay.entities.TransactionStatus;
import org.microworld.mangopay.entities.TransactionType;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;
import org.microworld.mangopay.entities.bankaccounts.IbanBankAccount;

public class PayOutIT extends AbstractIntegrationTest {
  @Test
  public void createPayOut() throws MalformedURLException, IOException {
    final NaturalUser user = (NaturalUser) getUserWithMoney(1000, EUR);
    final Wallet wallet = client.getUserService().getWallets(user.getId(), null, null).get(0);
    final BankAccount bankAccount = client.getBankAccountService().create(user.getId(), new IbanBankAccount(user.getFirstName() + " " + user.getLastName(), user.getAddress(), "FR7618829754160173622224154", "CMBRFR2BCME", null));

    final BankWirePayOut bankWirePayOut = client.getPayOutService().createBankWirePayOut(new BankWirePayOut(user.getId(), wallet.getId(), EUR, 100, 0, bankAccount.getId(), "Bank wire reference.", null));
    assertThat(bankWirePayOut, is(bankWirePayOut(Instant.now(), user.getId(), EUR, 100, 0, TransactionStatus.CREATED, wallet.getId(), bankAccount.getId(), "Bank wire reference.", null)));
  }

  private Matcher<BankWirePayOut> bankWirePayOut(final Instant creationDate, final String authorId, final Currency currency, final int debitedAmount, final int feesAmount, final TransactionStatus status, final String debitedWalletId, final String bankAccountId, final String bankWireReference, final String tag) {
    return allOf(asList(
        hasProperty("id", is(notNullValue())),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("authorId", is(equalTo(authorId))),
        hasProperty("creditedUserId", is(nullValue())),
        hasProperty("debitedFunds", is(equalTo(new Amount(currency, debitedAmount)))),
        hasProperty("creditedFunds", is(equalTo(new Amount(currency, debitedAmount)))),
        hasProperty("fees", is(equalTo(new Amount(currency, feesAmount)))),
        hasProperty("status", is(equalTo(status))),
        hasProperty("type", is(equalTo(TransactionType.PAYOUT))),
        hasProperty("nature", is(equalTo(TransactionNature.REGULAR))),
        hasProperty("debitedWalletId", is(equalTo(debitedWalletId))),
        hasProperty("bankAccountId", is(equalTo(bankAccountId))),
        hasProperty("bankWireRef", is(equalTo(bankWireReference))),
        hasProperty("tag", is(equalTo(tag)))));
  }
}
