package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public class OtherBankAccount extends BankAccount {
  @SerializedName("Country")
  private String country;
  @SerializedName("BIC")
  private String bic;
  @SerializedName("AccountNumber")
  private String accountNumber;

  public OtherBankAccount(final String ownerName, final Address ownerAddress, final String country, final String bic, final String accountNumber, final String tag) {
    this.type = BankAccountType.OTHER;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.country = country;
    this.bic = bic;
    this.accountNumber = accountNumber;
    this.tag = tag;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(final String country) {
    this.country = country;
  }

  public String getBic() {
    return bic;
  }

  public void setBic(final String bic) {
    this.bic = bic;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(final String accountNumber) {
    this.accountNumber = accountNumber;
  }
}
