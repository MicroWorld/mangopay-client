/**
 * Copyright © 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.entities;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

public class LegalUser extends User {
  @SerializedName("Name")
  private String name;
  @SerializedName("LegalPersonType")
  private LegalPersonType legalPersonType;
  @SerializedName("HeadquartersAddress")
  private Address headquartersAddress;
  @SerializedName("LegalRepresentativeFirstName")
  private String legalRepresentativeFirstName;
  @SerializedName("LegalRepresentativeLastName")
  private String legalRepresentativeLastName;
  @SerializedName("LegalRepresentativeAddress")
  private Address legalRepresentativeAddress;
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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

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

  public Address getHeadquartersAddress() {
    return headquartersAddress;
  }

  public void setHeadquartersAddress(final Address headquartersAddress) {
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

  public Address getLegalRepresentativeAddress() {
    return legalRepresentativeAddress;
  }

  public void setLegalRepresentativeAddress(final Address legalRepresentativeAddress) {
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
