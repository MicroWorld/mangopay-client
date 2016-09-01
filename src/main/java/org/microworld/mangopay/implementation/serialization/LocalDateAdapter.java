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
package org.microworld.mangopay.implementation.serialization;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  private static final long SECONDS_PER_DAY = 86400;

  @Override
  public JsonElement serialize(final LocalDate src, final Type typeOfSrc, final JsonSerializationContext context) {
    return new JsonPrimitive(toTimestamp(src));
  }

  @Override
  public LocalDate deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
    return toLocalDate(json.getAsLong());
  }

  public static long toTimestamp(final LocalDate date) {
    return date.toEpochDay() * SECONDS_PER_DAY;
  }

  public static LocalDate toLocalDate(final long timestamp) {
    return LocalDate.ofEpochDay(Math.floorDiv(timestamp, SECONDS_PER_DAY));
  }
}
