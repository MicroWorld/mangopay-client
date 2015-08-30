package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public class IbanBankAccount extends BankAccount {
  @SerializedName("IBAN")
  private final String iban;
  @SerializedName("BIC")
  private final String bic;

  public IbanBankAccount(final String ownerName, final Address ownerAddress, final String iban, final String bic, final String tag) {
    this.type = BankAccountType.IBAN;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.iban = iban;
    this.bic = bic;
    this.tag = tag;
  }

  public String getIban() {
    return iban;
  }

  public String getBic() {
    return bic;
  }
}
