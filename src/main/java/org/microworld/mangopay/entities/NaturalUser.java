/**
 * Copyright (C) 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.entities;

import java.time.LocalDate;

import com.google.gson.annotations.SerializedName;

public class NaturalUser extends User {
  @SerializedName("FirstName")
  private String firstName;
  @SerializedName("LastName")
  private String lastName;
  @SerializedName("Address")
  private Address address;
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

  public Address getAddress() {
    return address;
  }

  public void setAddress(final Address address) {
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
