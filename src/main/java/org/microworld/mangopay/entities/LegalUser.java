package org.microworld.mangopay.entities;

import java.time.LocalDate;

import com.google.gson.annotations.SerializedName;

public class LegalUser extends User {
  @SerializedName("Name")
  private String name;
  @SerializedName("LegalPersonType")
  private LegalPersonType legalPersonType;
  @SerializedName("HeadquartersAddress")
  private String headquartersAddress;
  @SerializedName("LegalRepresentativeFirstName")
  private String legalRepresentativeFirstName;
  @SerializedName("LegalRepresentativeLastName")
  private String legalRepresentativeLastName;
  @SerializedName("LegalRepresentativeAddress")
  private String legalRepresentativeAddress;
  @SerializedName("LegalRepresentativeEmail")
  private String legalRepresentativeEmail;
  @SerializedName("LegalRepresentativeBirthday")
  private LocalDate legalRepresentativeBirthday;
  @SerializedName("LegalRepresentativeNationality")
  private String legalRepresentativeNationality;
  @SerializedName("LegalRepresentativeCountryOfResidence")
  private String legalRepresentativeCountryOfResidence;
  @SerializedName("Statute")
  private String statute;
  @SerializedName("ProofOfRegistration")
  private String proofOfRegistration;
  @SerializedName("ShareholderDeclaration")
  private String shareholderDeclaration;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public LegalPersonType getLegalPersonType() {
    return legalPersonType;
  }

  public void setLegalPersonType(final LegalPersonType legalPersonType) {
    this.legalPersonType = legalPersonType;
  }

  public String getHeadquartersAddress() {
    return headquartersAddress;
  }

  public void setHeadquartersAddress(final String headquartersAddress) {
    this.headquartersAddress = headquartersAddress;
  }

  public String getLegalRepresentativeFirstName() {
    return legalRepresentativeFirstName;
  }

  public void setLegalRepresentativeFirstName(final String legalRepresentativeFirstName) {
    this.legalRepresentativeFirstName = legalRepresentativeFirstName;
  }

  public String getLegalRepresentativeLastName() {
    return legalRepresentativeLastName;
  }

  public void setLegalRepresentativeLastName(final String legalRepresentativeLastName) {
    this.legalRepresentativeLastName = legalRepresentativeLastName;
  }

  public String getLegalRepresentativeAddress() {
    return legalRepresentativeAddress;
  }

  public void setLegalRepresentativeAddress(final String legalRepresentativeAddress) {
    this.legalRepresentativeAddress = legalRepresentativeAddress;
  }

  public String getLegalRepresentativeEmail() {
    return legalRepresentativeEmail;
  }

  public void setLegalRepresentativeEmail(final String legalRepresentativeEmail) {
    this.legalRepresentativeEmail = legalRepresentativeEmail;
  }

  public LocalDate getLegalRepresentativeBirthday() {
    return legalRepresentativeBirthday;
  }

  public void setLegalRepresentativeBirthday(final LocalDate legalRepresentativeBirthday) {
    this.legalRepresentativeBirthday = legalRepresentativeBirthday;
  }

  public String getLegalRepresentativeNationality() {
    return legalRepresentativeNationality;
  }

  public void setLegalRepresentativeNationality(final String legalRepresentativeNationality) {
    this.legalRepresentativeNationality = legalRepresentativeNationality;
  }

  public String getLegalRepresentativeCountryOfResidence() {
    return legalRepresentativeCountryOfResidence;
  }

  public void setLegalRepresentativeCountryOfResidence(final String legalRepresentativeCountryOfResidence) {
    this.legalRepresentativeCountryOfResidence = legalRepresentativeCountryOfResidence;
  }

  public String getStatute() {
    return statute;
  }

  public String getProofOfRegistration() {
    return proofOfRegistration;
  }

  public String getShareholderDeclaration() {
    return shareholderDeclaration;
  }
}
