/**
 * Copyright (C) 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.entities;

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

public abstract class Entity {
  @SerializedName("Id")
  private String id;
  @SerializedName("CreationDate")
  private Instant creationDate;
  @SerializedName("Tag")
  protected String tag;

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
