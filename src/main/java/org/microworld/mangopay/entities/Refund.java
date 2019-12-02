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

import java.time.Instant;

public class Refund extends Entity {
    @SerializedName("DebitedFunds")
    private Amount debitedFunds;
    @SerializedName("CreditedFunds")
    private Amount creditedFunds;
    @SerializedName("Fees")
    private Amount fees;
    @SerializedName("DebitedWalletId")
    private String debitedWalletId;
    @SerializedName("CreditedWalletId")
    private String creditedWalletId;
    @SerializedName("AuthorId")
    private String authorId;
    @SerializedName("CreditedUserId")
    private String creditedUserId;
    @SerializedName("Nature")
    private TransactionNature nature;
    @SerializedName("Status")
    private TransactionStatus status;
    @SerializedName("ExecutionDate")
    private Instant executionDate;
    @SerializedName("ResultCode")
    private String resultCode;
    @SerializedName("ResultMessage")
    private String resultMessage;
    @SerializedName("Type")
    private TransactionType type;
    @SerializedName("InitialTransactionId")
    private String initialTransactionId;
    @SerializedName("InitialTransactionType")
    private TransactionType initialTransactionType;
    @SerializedName("RefundReason")
    private RefundReason refundReason;

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

    public Amount getDebitedFunds() {
        return debitedFunds;
    }

    public Amount getCreditedFunds() {
        return creditedFunds;
    }

    public Amount getFees() {
        return fees;
    }

    public String getDebitedWalletId() {
        return debitedWalletId;
    }

    public String getCreditedWalletId() {
        return creditedWalletId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getCreditedUserId() {
        return creditedUserId;
    }

    public TransactionNature getNature() {
        return nature;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Instant getExecutionDate() {
        return executionDate;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public TransactionType getType() {
        return type;
    }

    public String getInitialTransactionId() {
        return initialTransactionId;
    }

    public TransactionType getInitialTransactionType() {
        return initialTransactionType;
    }

    public RefundReason getRefundReason() {
        return refundReason;
    }
}
