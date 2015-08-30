package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public class BritishBankAccount extends BankAccount {
  @SerializedName("AccountNumber")
  private final String accountNumber;
  @SerializedName("SortCode")
  private final String sortCode;

  public BritishBankAccount(final String ownerName, final Address ownerAddress, final String accountNumber, final String sortCode, final String tag) {
    this.type = BankAccountType.GB;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.accountNumber = accountNumber;
    this.sortCode = sortCode;
    this.tag = tag;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getSortCode() {
    return sortCode;
  }
}
