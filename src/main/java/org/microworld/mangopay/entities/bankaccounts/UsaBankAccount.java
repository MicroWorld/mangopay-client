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

public class UsaBankAccount extends BankAccount {
  @SerializedName("AccountNumber")
  private String accountNumber;
  @SerializedName("ABA")
  private String aba;
  @SerializedName("DepositAccountType")
  private DepositAccountType depositAccountType;

  protected UsaBankAccount() {
    super();
  }

  public UsaBankAccount(final String ownerName, final Address ownerAddress, final String accountNumber, final String aba, final DepositAccountType depositAccountType, final String tag) {
    super();
    this.type = BankAccountType.US;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.accountNumber = accountNumber;
    this.aba = aba;
    this.depositAccountType = depositAccountType;
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
