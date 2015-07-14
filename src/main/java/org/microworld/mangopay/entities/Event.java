package org.microworld.mangopay.entities;

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

public class Event {
  @SerializedName("RessourceId")
  private String id;
  @SerializedName("EventType")
  private EventType type;
  @SerializedName("Date")
  private Instant date;

  public String getId() {
    return id;
  }

  public EventType getType() {
    return type;
  }

  public Instant getDate() {
    return date;
  }
}
