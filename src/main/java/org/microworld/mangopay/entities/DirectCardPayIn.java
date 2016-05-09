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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.gson.annotations.SerializedName;

public class DirectCardPayIn extends PayIn {
  @SerializedName("CardId")
  private final String cardId;
  @SerializedName("SecureMode")
  private final SecureMode secureMode;
  @SerializedName("SecureModeRedirectURL")
  private String secureModeRedirectUrl;
  @SerializedName("SecureModeReturnURL")
  private final String secureModeReturnUrl;

  public DirectCardPayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final String cardId, final Currency currency, final int debitedAmount, final int feesAmount, final SecureMode secureMode, final String secureModeReturnUrl, final String tag) {
    this.authorId = authorId;
    this.creditedUserId = creditedUserId;
    this.creditedWalletId = creditedWalletId;
    this.cardId = cardId;
    this.debitedFunds = new Amount(currency, debitedAmount);
    this.fees = new Amount(currency, feesAmount);
    this.secureMode = secureMode;
    this.secureModeReturnUrl = secureModeReturnUrl;
    this.tag = tag;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public String getCardId() {
    return cardId;
  }

  public SecureMode getSecureMode() {
    return secureMode;
  }

  public String getSecureModeRedirectUrl() {
    return secureModeRedirectUrl;
  }

  public String getSecureModeReturnUrl() {
    return secureModeReturnUrl;
  }
}
