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

public abstract class PayIn extends Transaction {
    @SerializedName("CreditedWalletId")
    protected String creditedWalletId;
    @SerializedName("DebitedWalletId")
    protected String debitedWalletId;
    @SerializedName("PaymentType")
    protected PayInType paymentType;
    @SerializedName("ExecutionType")
    protected ExecutionType executionType;

    public String getCreditedWalletId() {
        return creditedWalletId;
    }

    public String getDebitedWalletId() {
        return debitedWalletId;
    }

    public PayInType getPaymentType() {
        return paymentType;
    }

    public ExecutionType getExecutionType() {
        return executionType;
    }
}
