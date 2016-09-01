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
package org.microworld.mangopay.entities.bankaccounts;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.microworld.mangopay.entities.Address;

import com.google.gson.annotations.SerializedName;

public class CanadianBankAccount extends BankAccount {
  @SerializedName("BankName")
  private String bankName;
  @SerializedName("InstitutionNumber")
  private String institutionNumber;
  @SerializedName("BranchCode")
  private String branchCode;
  @SerializedName("AccountNumber")
  private String accountNumber;

  protected CanadianBankAccount() {
    super();
  }

  public CanadianBankAccount(final String ownerName, final Address ownerAddress, final String bankName, final String institutionNumber, final String branchCode, final String accountNumber, final String tag) {
    super();
    this.type = BankAccountType.CA;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.bankName = bankName;
    this.institutionNumber = institutionNumber;
    this.branchCode = branchCode;
    this.accountNumber = accountNumber;
    this.tag = tag;
  }

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
