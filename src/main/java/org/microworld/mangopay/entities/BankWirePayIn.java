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

import static java.util.Objects.requireNonNull;

import java.util.Currency;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;

import com.google.gson.annotations.SerializedName;

public class BankWirePayIn extends PayIn {
  @SerializedName("DeclaredDebitedFunds")
  private final Amount declaredDebitedFunds;
  @SerializedName("DeclaredFees")
  private final Amount declaredFees;
  @SerializedName("WireReference")
  private String wireReference;
  @SerializedName("BankAccount")
  private BankAccount bankAccount;

  public BankWirePayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final Currency currency, final int declaredDebitedAmount, final int declaredFees, final String tag) {
    this.authorId = requireNonNull(authorId, "Author ID must not be null.");
    this.creditedUserId = creditedUserId;
    this.creditedWalletId = requireNonNull(creditedWalletId, "Credited wallet ID must not be null.");
    this.declaredDebitedFunds = new Amount(requireNonNull(currency, "Currency must not be null."), declaredDebitedAmount);
    this.declaredFees = new Amount(requireNonNull(currency, "Currency must not be null."), declaredFees);
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

  public Amount getDeclaredDebitedFunds() {
    return declaredDebitedFunds;
  }

  public Amount getDeclaredFees() {
    return declaredFees;
  }

  public String getWireReference() {
    return wireReference;
  }

  public BankAccount getBankAccount() {
    return bankAccount;
  }
}
