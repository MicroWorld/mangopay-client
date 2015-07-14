package org.microworld.mangopay.entities;

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

public abstract class User {
  @SerializedName("Id")
  private String id;
  @SerializedName("Tag")
  private String tag;
  @SerializedName("Email")
  private String email;
  @SerializedName("PersonType")
  private PersonType personType;
  @SerializedName("KYCLevel")
  private KycLevel kycLevel;
  @SerializedName("CreationDate")
  private Instant creationDate;

  public String getId() {
    return id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(final String tag) {
    this.tag = tag;
  }

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

  public Instant getCreationDate() {
    return creationDate;
  }
}
