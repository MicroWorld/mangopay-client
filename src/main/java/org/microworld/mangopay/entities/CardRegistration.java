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
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class CardRegistration extends Entity {
  @SerializedName("UserId")
  private final String userId;
  @SerializedName("Currency")
  private final Currency currency;
  @SerializedName("AccessKey")
  private String accessKey;
  @SerializedName("PreregistrationData")
  private String preregistrationData;
  @SerializedName("CardRegistrationURL")
  private String cardRegistrationUrl;
  @SerializedName("RegistrationData")
  private String registrationData;
  @SerializedName("CardType")
  private final CardType cardType;
  @SerializedName("CardId")
  private String cardId;
  @SerializedName("ResultCode")
  private String resultCode;
  @SerializedName("ResultMessage")
  private String resultMessage;
  @SerializedName("Status")
  private CardRegistrationStatus status;

  public CardRegistration(final String userId, final Currency currency) {
    this(userId, currency, null);
  }

  public CardRegistration(final String userId, final Currency currency, final CardType cardType) {
    this(userId, currency, cardType, null);
  }

  public CardRegistration(final String userId, final Currency currency, final CardType cardType, final String tag) {
    this.userId = Objects.requireNonNull(userId);
    this.currency = Objects.requireNonNull(currency);
    this.cardType = cardType;
    setTag(tag);
  }

  public String getUserId() {
    return userId;
  }

  public Currency getCurrency() {
    return currency;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public String getPreregistrationData() {
    return preregistrationData;
  }

  public String getCardRegistrationUrl() {
    return cardRegistrationUrl;
  }

  public String getRegistrationData() {
    return registrationData;
  }

  public void setRegistrationData(final String registrationData) {
    this.registrationData = registrationData;
  }

  public CardType getType() {
    return cardType;
  }

  public String getCardId() {
    return cardId;
  }

  public String getResultCode() {
    return resultCode;
  }

  public String getResultMessage() {
    return resultMessage;
  }

  public CardRegistrationStatus getStatus() {
    return status;
  }
}