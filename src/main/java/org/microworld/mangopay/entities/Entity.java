package org.microworld.mangopay.entities;

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

public abstract class Entity {
  @SerializedName("Id")
  private String id;
  @SerializedName("CreationDate")
  private Instant creationDate;
  @SerializedName("Tag")
  private String tag;

  public String getId() {
    return id;
  }

  public Instant getCreationDate() {
    return creationDate;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(final String tag) {
    this.tag = tag;
  }
}
