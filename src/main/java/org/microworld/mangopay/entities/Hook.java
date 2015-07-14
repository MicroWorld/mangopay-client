package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;

public class Hook extends Entity {
  @SerializedName("EventType")
  private EventType eventType;
  @SerializedName("Url")
  private String url;
  @SerializedName("Status")
  private HookStatus status;
  @SerializedName("Validity")
  private HookValidity validity;

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(final EventType eventType) {
    this.eventType = eventType;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public HookStatus getStatus() {
    return status;
  }

  public void setStatus(final HookStatus status) {
    this.status = status;
  }

  public HookValidity getValidity() {
    return validity;
  }
}
