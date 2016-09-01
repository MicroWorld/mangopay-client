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

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

public class WebCardPayIn extends CardPayIn {
  @SerializedName("Culture")
  private final CultureCode cultureCode;
  @SerializedName("CardType")
  private final CardType cardType;
  @SerializedName("ReturnURL")
  private final String returnUrl;
  @SerializedName("RedirectURL")
  private String redirectUrl;
  @SerializedName("TemplateURLOptions")
  private final Map<String, String> templateUrlOptions = new HashMap<>();

  public WebCardPayIn(final String authorId, final String creditedUserId, final String creditedWalletId, final CardType cardType, final Currency currency, final int debitedAmount, final int feesAmount, final CultureCode cultureCode, final String statementDescriptor, final String returnUrl, final String templateUrl, final SecureMode secureMode, final String tag) {
    this.authorId = authorId;
    this.creditedUserId = creditedUserId;
    this.creditedWalletId = creditedWalletId;
    this.cardType = cardType;
    this.debitedFunds = new Amount(currency, debitedAmount);
    this.fees = new Amount(currency, feesAmount);
    this.cultureCode = cultureCode;
    this.returnUrl = returnUrl;
    this.templateUrlOptions.put("Payline", templateUrl);
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

  public CultureCode getCultureCode() {
    return cultureCode;
  }

  public CardType getCardType() {
    return cardType;
  }

  public String getReturnUrl() {
    return returnUrl;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public Map<String, String> getTemplateUrlOptions() {
    return templateUrlOptions == null ? emptyMap() : unmodifiableMap(templateUrlOptions);
  }
}
