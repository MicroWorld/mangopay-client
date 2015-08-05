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

import com.google.gson.annotations.SerializedName;

public class DirectCardPayIn extends Transaction {
  @SerializedName("CreditedWalletId")
  private String creditedWalletId;
  @SerializedName("DebitedWalletId")
  private String debitedWalletId;
  @SerializedName("CardId")
  private String cardId;
  @SerializedName("SecureMode")
  private SecureMode secureMode;
  @SerializedName("SecureModeRedirectURL")
  private String secureModeRedirectUrl;
  @SerializedName("SecureModeReturnURL")
  private String secureModeReturnUrl;
  @SerializedName("PaymentType")
  private TransactionPaymentType paymentType;
  @SerializedName("ExecutionType")
  private TransactionExecutionType executionType;

  public void setAuthorId(final String authorId) {
    this.authorId = authorId;
  }

  public void setCreditedUserId(final String creditedUserId) {
    this.creditedUserId = creditedUserId;
  }

  public String getCreditedWalletId() {
    return creditedWalletId;
  }

  public void setCreditedWalletId(final String creditedWalletId) {
    this.creditedWalletId = creditedWalletId;
  }

  public String getDebitedWalletId() {
    return debitedWalletId;
  }

  public void setDebitedFunds(final Amount debitedFunds) {
    this.debitedFunds = debitedFunds;
  }

  public void setFees(final Amount fees) {
    this.fees = fees;
  }

  public String getCardId() {
    return cardId;
  }

  public void setCardId(final String cardId) {
    this.cardId = cardId;
  }

  public SecureMode getSecureMode() {
    return secureMode;
  }

  public void setSecureMode(final SecureMode secureMode) {
    this.secureMode = secureMode;
  }

  public String getSecureModeRedirectUrl() {
    return secureModeRedirectUrl;
  }

  public String getSecureModeReturnUrl() {
    return secureModeReturnUrl;
  }

  public void setSecureModeReturnUrl(final String secureModeReturnUrl) {
    this.secureModeReturnUrl = secureModeReturnUrl;
  }

  public TransactionPaymentType getPaymentType() {
    return paymentType;
  }

  public TransactionExecutionType getExecutionType() {
    return executionType;
  }
}
