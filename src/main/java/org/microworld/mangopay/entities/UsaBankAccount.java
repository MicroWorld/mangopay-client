package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public class UsaBankAccount extends BankAccount {
  @SerializedName("AccountNumber")
  private final String accountNumber;
  @SerializedName("ABA")
  private final String aba;
  @SerializedName("DepositAccountType")
  private final DepositAccountType depositAccountType;

  public UsaBankAccount(final String ownerName, final Address ownerAddress, final String accountNumber, final String aba, final DepositAccountType depositAccountType, final String tag) {
    this.type = BankAccountType.US;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.accountNumber = accountNumber;
    this.aba = aba;
    this.depositAccountType = depositAccountType;
    this.tag = tag;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getAba() {
    return aba;
  }

  public DepositAccountType getDepositAccountType() {
    return depositAccountType;
  }
}
