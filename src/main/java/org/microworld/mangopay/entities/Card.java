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

import java.time.YearMonth;
import java.util.Currency;

public class Card extends Entity {
    @SerializedName("ExpirationDate")
    private YearMonth expirationDate;
    @SerializedName("Alias")
    private String alias;
    @SerializedName("CardProvider")
    private String cardProvider;
    @SerializedName("CardType")
    private CardType cardType;
    @SerializedName("Country")
    private String country;
    @SerializedName("Product")
    private String product;
    @SerializedName("BankCode")
    private String bankCode;
    @SerializedName("Active")
    private boolean active;
    @SerializedName("Currency")
    private Currency currency;
    @SerializedName("Validity")
    private CardValidity validity;
    @SerializedName("UserId")
    private String userId;

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

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public String getAlias() {
        return alias;
    }

    public String getCardProvider() {
        return cardProvider;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getCountry() {
        return country;
    }

    public String getProduct() {
        return product;
    }

    public String getBankCode() {
        return bankCode;
    }

    public boolean isActive() {
        return active;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CardValidity getValidity() {
        return validity;
    }

    public String getUserId() {
        return userId;
    }
}
