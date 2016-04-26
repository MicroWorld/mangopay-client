package org.microworld.mangopay.entities;

import java.util.Currency;

import com.google.gson.annotations.SerializedName;

public class BankWirePayOut extends Transaction {
  @SerializedName("DebitedWalletId")
  private final String debitedWalletId;
  @SerializedName("BankAccountId")
  private final String bankAccountId;
  @SerializedName("BankWireRef")
  private final String bankWireRef;

  public BankWirePayOut(final String authorId, final String debitedWalletId, final Currency currency, final int debitedAmount, final int feesAmount, final String bankAccountId, final String bankWireReference, final String tag) {
    this.authorId = authorId;
    this.debitedWalletId = debitedWalletId;
    this.debitedFunds = new Amount(currency, debitedAmount);
    this.fees = new Amount(currency, feesAmount);
    this.bankAccountId = bankAccountId;
    this.bankWireRef = bankWireReference;
    this.tag = tag;
  }

  public String getDebitedWalletId() {
    return debitedWalletId;
  }

  public String getBankAccountId() {
    return bankAccountId;
  }

  public String getBankWireRef() {
    return bankWireRef;
  }
}
