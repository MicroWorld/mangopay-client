package org.microworld.mangopay.entities.kyc;

import org.microworld.mangopay.entities.Entity;

import com.google.gson.annotations.SerializedName;

public class KycDocument extends Entity {
  @SerializedName("Type")
  private final KycDocumentType type;
  @SerializedName("Status")
  private KycDocumentStatus status;
  @SerializedName("RefusedReasonType")
  private String refusedReasonType;
  @SerializedName("RefusedReasonMessage")
  private String refusedReasonMessage;

  public KycDocument(final KycDocumentType type, final String tag) {
    this.type = type;
    this.tag = tag;
  }

  public void setStatusToValidationAsked() {
    this.status = KycDocumentStatus.VALIDATION_ASKED;
  }

  public KycDocumentType getType() {
    return type;
  }

  public KycDocumentStatus getStatus() {
    return status;
  }

  public String getRefusedReasonType() {
    return refusedReasonType;
  }

  public String getRefusedReasonMessage() {
    return refusedReasonMessage;
  }
}
