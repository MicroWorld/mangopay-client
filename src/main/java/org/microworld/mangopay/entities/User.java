package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public abstract class User extends Entity {
  @SerializedName("Email")
  private String email;
  @SerializedName("PersonType")
  private PersonType personType;
  @SerializedName("KYCLevel")
  private KycLevel kycLevel;

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public PersonType getPersonType() {
    return personType;
  }

  public KycLevel getKycLevel() {
    return kycLevel;
  }
}
