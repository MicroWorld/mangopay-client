package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public class Token {
  public static final String HEADER_NAME = "Authorization";
  private static final long NANOS = 1000000000L;
  private static final int SAFETY_MARGIN = 5;
  @SerializedName("access_token")
  private String value;
  @SerializedName("token_type")
  private String type;
  @SerializedName("expires_in")
  private int duration;
  private final long creationTime = System.nanoTime();

  public boolean isExpired() {
    return System.nanoTime() > creationTime + (duration - SAFETY_MARGIN) * NANOS;
  }

  public String getHeaderValue() {
    return type + " " + value;
  }

  public String getValue() {
    return value;
  }

  public String getType() {
    return type;
  }

  public int getDuration() {
    return duration;
  }

  public long getCreationTime() {
    return creationTime;
  }
}
