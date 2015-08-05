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

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

public class Transaction extends Entity {
  @SerializedName("AuthorId")
  protected String authorId;
  @SerializedName("CreditedUserId")
  protected String creditedUserId;
  @SerializedName("DebitedFunds")
  protected Amount debitedFunds;
  @SerializedName("CreditedFunds")
  private Amount creditedFunds;
  @SerializedName("Fees")
  protected Amount fees;
  @SerializedName("Status")
  private TransactionStatus status;
  @SerializedName("ResultCode")
  private String resultCode;
  @SerializedName("ResultMessage")
  private String resultMessage;
  @SerializedName("ExecutionDate")
  private Instant executionDate;
  @SerializedName("Type")
  private TransactionType type;
  @SerializedName("Nature")
  private TransactionNature nature;

  public String getAuthorId() {
    return authorId;
  }

  public String getCreditedUserId() {
    return creditedUserId;
  }

  public Amount getDebitedFunds() {
    return debitedFunds;
  }

  public Amount getCreditedFunds() {
    return creditedFunds;
  }

  public Amount getFees() {
    return fees;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public String getResultCode() {
    return resultCode;
  }

  public String getResultMessage() {
    return resultMessage;
  }

  public Instant getExecutionDate() {
    return executionDate;
  }

  public TransactionType getType() {
    return type;
  }

  public TransactionNature getNature() {
    return nature;
  }
}
