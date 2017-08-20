package org.microworld.mangopay.tests;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.microworld.mangopay.entities.RefundReasonType.OTHER;
import static org.microworld.mangopay.entities.TransactionNature.REFUND;
import static org.microworld.mangopay.entities.TransactionStatus.SUCCEEDED;
import static org.microworld.mangopay.entities.TransactionType.TRANSFER;
import static org.microworld.test.Matchers.around;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Currency;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.microworld.mangopay.entities.Amount;
import org.microworld.mangopay.entities.Refund;
import org.microworld.mangopay.entities.RefundReason;
import org.microworld.mangopay.entities.TransactionStatus;
import org.microworld.mangopay.entities.TransactionType;
import org.microworld.mangopay.entities.Transfer;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.exceptions.MangopayException;

public class RefundIT extends AbstractIntegrationTest {
  @Test
  public void getRefund() throws MalformedURLException, IOException {
    final User debitedUser = getUserWithMoney(2000, EUR);
    final Wallet debitedWallet = client.getUserService().getWallets(debitedUser.getId(), null, null).get(0);
    final User creditedUser = client.getUserService().create(randomNaturalUser());
    final Wallet creditedWallet = client.getWalletService().create(new Wallet(creditedUser.getId(), EUR, "Wallet to be credited", null));
    final Transfer transfer = client.getTransferService().create(new Transfer(debitedUser.getId(), debitedWallet.getId(), creditedWallet.getId(), EUR, 2000, 0, null));

    final Refund refund = client.getTransferService().refund(transfer.getId(), transfer.getAuthorId(), null);
    final Refund fetchedRefund = client.getRefundService().get(refund.getId());
    assertThat(fetchedRefund, is(equalTo(refund)));
    assertThat(fetchedRefund, is(refund(transfer.getAuthorId(), creditedWallet.getId(), null, debitedWallet.getId(), EUR, 2000, 0, SUCCEEDED, "000000", "Success", null, Instant.now(), TRANSFER, transfer.getId(), TRANSFER)));
  }

  @Test
  public void getRefundWithInvalidId() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("ressource_not_found: The ressource does not exist");
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource Refund with the id=10");
    client.getRefundService().get("10");
  }

  private Matcher<Refund> refund(final String authorId, final String debitedWalletId, final String creditedUserId, final String creditedWalletId, final Currency currency, final int debitedAmount, final int feesAmount, final TransactionStatus status, final String resultCode, final String resultMessage, final String tag, final Instant creationDate, final TransactionType type, final String initialTransactionId, final TransactionType initialTransactionType) {
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
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("executionDate", is(around(creationDate))),
        hasProperty("type", is(equalTo(type))),
        hasProperty("nature", is(equalTo(REFUND))),
        hasProperty("initialTransactionId", is(equalTo(initialTransactionId))),
        hasProperty("initialTransactionType", is(equalTo(initialTransactionType))),
        hasProperty("refundReason", is(equalTo(new RefundReason(OTHER, null))))));
  }
}
