/**
 * Copyright © 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

  public void setTag(final String tag) {
    this.tag = tag;
  }

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
