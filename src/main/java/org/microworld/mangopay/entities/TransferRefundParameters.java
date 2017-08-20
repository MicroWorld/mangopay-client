package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public class TransferRefundParameters {
  @SerializedName("AuthorId")
  private final String authorId;
  @SerializedName("Tag")
  private final String tag;

  public TransferRefundParameters(final String authorId, final String tag) {
    this.authorId = authorId;
    this.tag = tag;
  }

  public String getAuthorId() {
    return authorId;
  }

  public String getTag() {
    return tag;
  }
}
