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

public class PayInRefundParameters {
    @SerializedName("AuthorId")
    private final String authorId;
    @SerializedName("DebitedFunds")
    private final Amount debitedFunds;
    @SerializedName("Fees")
    private final Amount fees;
    @SerializedName("Tag")
    private final String tag;

    public PayInRefundParameters(final String authorId, final Amount debitedFunds, final Amount fees, final String tag) {
        this.authorId = authorId;
        this.debitedFunds = debitedFunds;
        this.fees = fees;
        this.tag = tag;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Amount getDebitedFunds() {
        return debitedFunds;
    }

    public Amount getFees() {
        return fees;
    }

    public String getTag() {
        return tag;
    }
}
