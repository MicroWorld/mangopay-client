package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public abstract class BankAccount extends Entity {
  @SerializedName("Type")
  protected BankAccountType type;
  @SerializedName("OwnerName")
  protected String ownerName;
  @SerializedName("OwnerAddress")
  protected Address ownerAddress;
  @SerializedName("UserId")
  protected String userId;

  public BankAccountType getType() {
    return type;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public Address getOwnerAddress() {
    return ownerAddress;
  }

  public String getUserId() {
    return userId;
  }
}
