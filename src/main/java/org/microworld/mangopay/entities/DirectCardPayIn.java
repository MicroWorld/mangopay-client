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

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Currency;

public class DirectCardPayIn extends CardPayIn {
    @SerializedName("CardId")
    private final String cardId;
    @SerializedName("SecureModeReturnURL")
    private final String secureModeReturnUrl;
    @SerializedName("SecureModeRedirectURL")
    private String secureModeRedirectUrl;

    public DirectCardPayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final String cardId, final Currency currency, final int debitedAmount, final int feesAmount, final String statementDescriptor, final String secureModeReturnUrl, final SecureMode secureMode, final String tag) {
        this.authorId = authorId;
        this.creditedUserId = creditedUserId;
        this.creditedWalletId = creditedWalletId;
        this.cardId = cardId;
        this.debitedFunds = new Amount(currency, debitedAmount);
        this.fees = new Amount(currency, feesAmount);
        this.secureModeReturnUrl = secureModeReturnUrl;
        this.secureMode = secureMode;
        this.statementDescriptor = statementDescriptor;
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

    public String getCardId() {
        return cardId;
    }

    public String getSecureModeRedirectUrl() {
        return secureModeRedirectUrl;
    }

    public String getSecureModeReturnUrl() {
        return secureModeReturnUrl;
    }
}
