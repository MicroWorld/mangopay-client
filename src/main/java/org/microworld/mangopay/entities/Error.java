package org.microworld.mangopay.entities;

import java.time.Instant;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Error {
  @SerializedName("Id")
  private String id;
  @SerializedName("Type")
  private String type;
  @SerializedName("Message")
  private String message;
  @SerializedName("Date")
  private Instant date;
  @SerializedName("errors")
  private Map<String, String> errors;

  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public Instant getDate() {
    return date;
  }

  public Map<String, String> getErrors() {
    return errors;
  }
}
