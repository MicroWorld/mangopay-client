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

import java.util.Currency;

import com.google.gson.annotations.SerializedName;

public class Amount {
  @SerializedName("Currency")
  private final Currency currency;
  @SerializedName("Amount")
  private final int cents;

  public Amount(final Currency currency, final int cents) {
    this.currency = currency;
    this.cents = cents;
  }

  @Override
  public String toString() {
    return "Amount [currency=" + currency + ", cents=" + cents + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + cents;
    result = prime * result + (currency == null ? 0 : currency.getCurrencyCode().hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Amount other = (Amount) obj;
    if (cents != other.cents) {
      return false;
    }
    if (currency == null) {
      if (other.currency != null) {
        return false;
      }
    } else if (other.currency == null || !currency.getCurrencyCode().equals(other.currency.getCurrencyCode())) {
      return false;
    }
    return true;
  }

  public Currency getCurrency() {
    return currency;
  }

  public int getCents() {
    return cents;
  }
}
