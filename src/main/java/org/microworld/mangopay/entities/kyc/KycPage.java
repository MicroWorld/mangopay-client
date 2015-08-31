package org.microworld.mangopay.entities.kyc;

import com.google.gson.annotations.SerializedName;

public class KycPage {
  @SerializedName("File")
  private final String base64content;

  public KycPage(final String base64content) {
    this.base64content = base64content;
  }
}
