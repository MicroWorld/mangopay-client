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
package org.microworld.mangopay.entities.bankaccounts;

import org.microworld.mangopay.entities.Address;

import com.google.gson.annotations.SerializedName;

public class UsaBankAccount extends BankAccount {
  @SerializedName("AccountNumber")
  private final String accountNumber;
  @SerializedName("ABA")
  private final String aba;
  @SerializedName("DepositAccountType")
  private final DepositAccountType depositAccountType;

  public UsaBankAccount(final String ownerName, final Address ownerAddress, final String accountNumber, final String aba, final DepositAccountType depositAccountType, final String tag) {
    this.type = BankAccountType.US;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.accountNumber = accountNumber;
    this.aba = aba;
    this.depositAccountType = depositAccountType;
    this.tag = tag;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getAba() {
    return aba;
  }

  public DepositAccountType getDepositAccountType() {
    return depositAccountType;
  }
}