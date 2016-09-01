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
