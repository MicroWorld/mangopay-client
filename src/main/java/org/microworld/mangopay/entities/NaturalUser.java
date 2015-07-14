package org.microworld.mangopay.entities;

import java.time.LocalDate;

import com.google.gson.annotations.SerializedName;

public class NaturalUser extends User {
  @SerializedName("FirstName")
  private String firstName;
  @SerializedName("LastName")
  private String lastName;
  @SerializedName("Address")
  private String address;
  @SerializedName("Birthday")
  private LocalDate birthday;
  @SerializedName("Nationality")
  private String nationality;
  @SerializedName("CountryOfResidence")
  private String countryOfResidence;
  @SerializedName("Occupation")
  private String occupation;
  @SerializedName("IncomeRange")
  private IncomeRange incomeRange;
  @SerializedName("ProofOfIdentity")
  private String proofOfIdentity;
  @SerializedName("ProofOfAddress")
  private String proofOfAddress;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(final LocalDate birthday) {
    this.birthday = birthday;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(final String nationality) {
    this.nationality = nationality;
  }

  public String getCountryOfResidence() {
    return countryOfResidence;
  }

  public void setCountryOfResidence(final String countryOfResidence) {
    this.countryOfResidence = countryOfResidence;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(final String occupation) {
    this.occupation = occupation;
  }

  public IncomeRange getIncomeRange() {
    return incomeRange;
  }

  public void setIncomeRange(final IncomeRange incomeRange) {
    this.incomeRange = incomeRange;
  }

  public String getProofOfIdentity() {
    return proofOfIdentity;
  }

  public String getProofOfAddress() {
    return proofOfAddress;
  }
}
