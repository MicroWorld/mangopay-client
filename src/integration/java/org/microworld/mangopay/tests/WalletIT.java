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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.microworld.mangopay.entities.FundsType.DEFAULT;
import static org.microworld.mangopay.search.SortDirection.DESCENDING;
import static org.microworld.mangopay.search.SortField.CREATION_DATE;
import static org.microworld.test.Matchers.around;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Currency;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.microworld.mangopay.entities.Amount;
import org.microworld.mangopay.entities.DirectCardPayIn;
import org.microworld.mangopay.entities.FundsType;
import org.microworld.mangopay.entities.SecureMode;
import org.microworld.mangopay.entities.Transaction;
import org.microworld.mangopay.entities.TransactionType;
import org.microworld.mangopay.entities.Transfer;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.exceptions.MangopayException;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public class WalletIT extends AbstractIntegrationTest {
  @Test
  public void createGetUpdateWallet() {
    final User walletOwner = client.getUserService().create(randomNaturalUser());

    final Wallet createdWallet = client.getWalletService().create(new Wallet(walletOwner.getId(), EUR, "Euro account", null));
    assertThat(createdWallet, is(wallet(walletOwner.getId(), EUR, "Euro account", null, Instant.now(), 0, DEFAULT)));

    final Wallet fetchedWallet = client.getWalletService().get(createdWallet.getId());
    assertThat(fetchedWallet, is(wallet(walletOwner.getId(), EUR, "Euro account", null, Instant.now(), 0, DEFAULT)));
    assertThat(fetchedWallet.getId(), is(equalTo(createdWallet.getId())));

    fetchedWallet.setDescription("EUR account");
    fetchedWallet.setTag("Something");
    final Wallet updatedWallet = client.getWalletService().update(fetchedWallet);
    assertThat(updatedWallet, is(wallet(walletOwner.getId(), EUR, "EUR account", "Something", Instant.now(), 0, DEFAULT)));
    assertThat(updatedWallet.getId(), is(equalTo(fetchedWallet.getId())));
  }

  @Test
  public void getWalletWithInvalidId() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("ressource_not_found: The ressource does not exist");
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource Wallet with the id=10");
    client.getWalletService().get("10");
  }

  @Test
  public void createWalletWithMissingCurrency() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Currency: The Currency field is required.");
    client.getWalletService().create(new Wallet("7589576", null, "The description", null));
  }

  @Test
  public void createWalletWithMissingDescription() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Description: The Description field is required.");
    client.getWalletService().create(new Wallet("7589576", USD, null, null));
  }

  @Test
  public void createWalletWithMissingOwner() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("externalOwnerId: The value  is not valid");
    client.getWalletService().create(new Wallet(null, USD, "The description", null));
  }

  @Test
  public void createWalletWithFieldsContainingInvalidValues() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("currency_not_available: Error: the currency used is not available");
    thrown.expectMessage("currency: The currency XAF is not available or has been disabled");
    client.getWalletService().create(new Wallet("10", XAF, "Invalid wallet", null));
  }

  @Test
  public void listWalletTransactions() throws MalformedURLException, IOException, InterruptedException {
    final User user1 = client.getUserService().create(randomNaturalUser());
    final Wallet wallet1 = client.getWalletService().create(new Wallet(user1.getId(), EUR, "wallet", null));
    final String cardId = registerCard(user1, EUR, "4970100000000154", "1218", "123").getCardId();
    final User user2 = client.getUserService().create(randomNaturalUser());
    final Wallet wallet2 = client.getWalletService().create(new Wallet(user2.getId(), EUR, "Wallet to be credited", null));

    client.getPayInService().createDirectCardPayIn(new DirectCardPayIn(user1.getId(), user1.getId(), wallet1.getId(), cardId, EUR, 4000, 0, null, "https://foo.bar", SecureMode.DEFAULT, null));
    Thread.sleep(2000);
    client.getTransferService().create(new Transfer(user1.getId(), wallet1.getId(), wallet2.getId(), EUR, 2000, 0, null));

    final List<Transaction> transactions = client.getWalletService().getTransactions(wallet1.getId(), Sort.by(CREATION_DATE, DESCENDING), Page.of(1));
    assertThat(transactions, hasSize(2));
    assertThat(transactions.get(0).getType(), is(equalTo(TransactionType.TRANSFER)));
    assertThat(transactions.get(1).getType(), is(equalTo(TransactionType.PAYIN)));
  }

  private Matcher<Wallet> wallet(final String ownerId, final Currency currency, final String description, final String tag, final Instant creationDate, final int balance, final FundsType fundsType) {
    return allOf(asList(
        hasProperty("id", is(notNullValue())),
        hasProperty("ownerId", is(equalTo(ownerId))),
        hasProperty("currency", is(equalTo(currency))),
        hasProperty("description", is(equalTo(description))),
        hasProperty("tag", is(equalTo(tag))),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("balance", is(equalTo(new Amount(currency, balance)))),
        hasProperty("fundsType", is(equalTo(fundsType)))));
  }
}
