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

import com.google.gson.annotations.SerializedName;

public class CanadianBankAccount extends BankAccount {
  @SerializedName("BankName")
  private final String bankName;
  @SerializedName("InstitutionNumber")
  private final String institutionNumber;
  @SerializedName("BranchCode")
  private final String branchCode;
  @SerializedName("AccountNumber")
  private final String accountNumber;

  public CanadianBankAccount(final String ownerName, final Address ownerAddress, final String bankName, final String institutionNumber, final String branchCode, final String accountNumber, final String tag) {
    this.type = BankAccountType.CA;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.bankName = bankName;
    this.institutionNumber = institutionNumber;
    this.branchCode = branchCode;
    this.accountNumber = accountNumber;
    this.tag = tag;
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
