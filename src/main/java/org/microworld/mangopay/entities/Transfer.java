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

public class Transfer extends Transaction {
  @SerializedName("DebitedWalletId")
  private final String debitedWalletId;
  @SerializedName("CreditedWalletId")
  private final String creditedWalletId;

  public Transfer(final String authorId, final String debitedWalletId, final String creditedWalletId, final Currency currency, final int debitedAmount, final int feesAmount, final String tag) {
    this.authorId = authorId;
    this.debitedWalletId = debitedWalletId;
    this.creditedWalletId = creditedWalletId;
    this.debitedFunds = new Amount(currency, debitedAmount);
    this.fees = new Amount(currency, feesAmount);
    this.setTag(tag);
  }

  public String getDebitedWalletId() {
    return debitedWalletId;
  }

  public String getCreditedWalletId() {
    return creditedWalletId;
  }
}
