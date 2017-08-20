package org.microworld.mangopay.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

public class RefundReason {
  @SerializedName("RefundReasonType")
  private final RefundReasonType type;
  @SerializedName("RefundReasonMessage")
  private final String message;

  public RefundReason(final RefundReasonType type, final String message) {
    this.type = type;
    this.message = message;
  }

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

  public RefundReasonType getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }
}
