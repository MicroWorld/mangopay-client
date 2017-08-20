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
