package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public class CanadianBankAccount extends BankAccount {
  @SerializedName("BankName")
  private final String bankName;
  @SerializedName("InstitutionNumber")
  private final String institutionNumber;
  @SerializedName("BranchCode")
  private final String branchCode;
  @SerializedName("AccountNumber")
  private final String accountNumber;

  public CanadianBankAccount(final String ownerName, final Address ownerAddress, final String bankName, final String institutionNumber, final String branchCode, final String accountNumber, final String tag) {
    this.type = BankAccountType.CA;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.bankName = bankName;
    this.institutionNumber = institutionNumber;
    this.branchCode = branchCode;
    this.accountNumber = accountNumber;
    this.tag = tag;
  }

  public String getBankName() {
    return bankName;
  }

  public String getInstitutionNumber() {
    return institutionNumber;
  }

  public String getBranchCode() {
    return branchCode;
  }

  public String getAccountNumber() {
    return accountNumber;
  }
}
