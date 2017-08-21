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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.microworld.mangopay.entities.TransactionStatus.FAILED;
import static org.microworld.mangopay.entities.TransactionStatus.SUCCEEDED;
import static org.microworld.test.Matchers.around;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Currency;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.microworld.mangopay.entities.Amount;
import org.microworld.mangopay.entities.TransactionStatus;
import org.microworld.mangopay.entities.Transfer;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.exceptions.MangopayException;

public class TransferIT extends AbstractIntegrationTest {
  @Test
  public void createAndGetAndRefundTransferWithNoFees() throws MalformedURLException, IOException {
    final User debitedUser = getUserWithMoney(2000, EUR);
    final Wallet debitedWallet = client.getUserService().getWallets(debitedUser.getId(), null, null).get(0);
    final User creditedUser = client.getUserService().create(randomNaturalUser());
    final Wallet creditedWallet = client.getWalletService().create(new Wallet(creditedUser.getId(), EUR, "Wallet to be credited", null));

    final Transfer transfer = client.getTransferService().create(new Transfer(debitedUser.getId(), debitedWallet.getId(), creditedWallet.getId(), EUR, 2000, 0, null));
    assertThat(transfer, is(transfer(debitedUser.getId(), debitedWallet.getId(), creditedUser.getId(), creditedWallet.getId(), EUR, 2000, 0, SUCCEEDED, "000000", "Success", null, Instant.now())));
    final Transfer fetchedTransfer = client.getTransferService().get(transfer.getId());
    assertThat(fetchedTransfer, is(transfer(debitedUser.getId(), debitedWallet.getId(), creditedUser.getId(), creditedWallet.getId(), EUR, 2000, 0, SUCCEEDED, "000000", "Success", null, Instant.now())));
    assertThat(fetchedTransfer.getId(), is(equalTo(transfer.getId())));

    assertThat(client.getWalletService().get(debitedWallet.getId()).getBalance().getCents(), is(equalTo(0)));
    assertThat(client.getWalletService().get(creditedWallet.getId()).getBalance().getCents(), is(equalTo(2000)));
    client.getTransferService().refund(transfer.getId(), transfer.getAuthorId(), null);
    assertThat(client.getWalletService().get(debitedWallet.getId()).getBalance().getCents(), is(equalTo(2000)));
    assertThat(client.getWalletService().get(creditedWallet.getId()).getBalance().getCents(), is(equalTo(0)));
  }

  @Test
  public void createGetAndRefundTransferWithFees() throws MalformedURLException, IOException {
    final User debitedUser = getUserWithMoney(2000, EUR);
    final Wallet debitedWallet = client.getUserService().getWallets(debitedUser.getId(), null, null).get(0);
    final User creditedUser = client.getUserService().create(randomNaturalUser());
    final Wallet creditedWallet = client.getWalletService().create(new Wallet(creditedUser.getId(), EUR, "Wallet to be credited", null));

    final Transfer transfer = client.getTransferService().create(new Transfer(debitedUser.getId(), debitedWallet.getId(), creditedWallet.getId(), EUR, 1000, 500, null));
    assertThat(transfer, is(transfer(debitedUser.getId(), debitedWallet.getId(), creditedUser.getId(), creditedWallet.getId(), EUR, 1000, 500, SUCCEEDED, "000000", "Success", null, Instant.now())));
    final Transfer fetchedTransfer = client.getTransferService().get(transfer.getId());
    assertThat(fetchedTransfer, is(transfer(debitedUser.getId(), debitedWallet.getId(), creditedUser.getId(), creditedWallet.getId(), EUR, 1000, 500, SUCCEEDED, "000000", "Success", null, Instant.now())));
    assertThat(fetchedTransfer.getId(), is(equalTo(transfer.getId())));

    assertThat(client.getWalletService().get(debitedWallet.getId()).getBalance().getCents(), is(equalTo(1000)));
    assertThat(client.getWalletService().get(creditedWallet.getId()).getBalance().getCents(), is(equalTo(500)));
    client.getTransferService().refund(transfer.getId(), transfer.getAuthorId(), null);
    assertThat(client.getWalletService().get(debitedWallet.getId()).getBalance().getCents(), is(equalTo(2000)));
    assertThat(client.getWalletService().get(creditedWallet.getId()).getBalance().getCents(), is(equalTo(0)));
  }

  @Test
  public void createTransferFromAWalletWithoutEnoughMoney() {
    final User debitedUser = client.getUserService().create(randomNaturalUser());
    final Wallet debitedWallet = client.getWalletService().create(new Wallet(debitedUser.getId(), EUR, "Wallet to be debited", null));
    final User creditedUser = client.getUserService().create(randomNaturalUser());
    final Wallet creditedWallet = client.getWalletService().create(new Wallet(creditedUser.getId(), EUR, "Wallet to be credited", null));

    final Transfer transfer = client.getTransferService().create(new Transfer(debitedUser.getId(), debitedWallet.getId(), creditedWallet.getId(), EUR, 20000, 0, null));
    assertThat(transfer, is(transfer(debitedUser.getId(), debitedWallet.getId(), creditedUser.getId(), creditedWallet.getId(), EUR, 20000, 0, FAILED, "001001", "Unsufficient wallet balance", null, Instant.now())));
  }

  @Test
  public void getTransferWithInvalidId() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("ressource_not_found: The ressource does not exist");
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource Transfer with the id=10");
    client.getTransferService().get("10");
  }

  @Test
  public void createTransferWithMissingMandatoryFields() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("CreditedWalletId: The CreditedWalletId field is required.");
    client.getTransferService().create(new Transfer("8252994", "8252995", null, EUR, 2000, 0, null));
  }

  @Test
  public void createTransferWithFieldsContainingInvalidValues() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("currency_not_available: Error: the currency used is not available");
    thrown.expectMessage("currency: The currency XAF is not available or has been disabled");
    client.getTransferService().create(new Transfer("8252994", "8252995", "8253004", XAF, 2000, 0, null));
  }

  @Test
  public void refundTransferWithInvalidId() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("ressource_not_found: The ressource does not exist");
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource Transfer with the id=10");
    client.getTransferService().refund("10", "11", null);
  }

  private Matcher<Transfer> transfer(final String authorId, final String debitedWalletId, final String creditedUserId, final String creditedWalletId, final Currency currency, final int debitedAmount, final int feesAmount, final TransactionStatus status, final String resultCode, final String resultMessage, final String tag, final Instant creationDate) {
    return allOf(asList(
        hasProperty("id", is(notNullValue())),
        hasProperty("authorId", is(equalTo(authorId))),
        hasProperty("debitedWalletId", is(equalTo(debitedWalletId))),
        hasProperty("creditedUserId", is(equalTo(creditedUserId))),
        hasProperty("creditedWalletId", is(equalTo(creditedWalletId))),
        hasProperty("debitedFunds", is(equalTo(new Amount(currency, debitedAmount)))),
        hasProperty("creditedFunds", is(equalTo(new Amount(currency, debitedAmount - feesAmount)))),
        hasProperty("fees", is(equalTo(new Amount(currency, feesAmount)))),
        hasProperty("status", is(equalTo(status))),
        hasProperty("resultCode", is(equalTo(resultCode))),
        hasProperty("resultMessage", is(equalTo(resultMessage))),
        hasProperty("tag", is(equalTo(tag))),
        hasProperty("creationDate", is(around(creationDate)))));
  }
}
