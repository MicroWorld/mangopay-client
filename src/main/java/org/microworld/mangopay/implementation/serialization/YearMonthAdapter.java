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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class YearMonthAdapter implements JsonSerializer<YearMonth>, JsonDeserializer<YearMonth> {
  private static final DateTimeFormatter YEAR_MONTH_PATTERN = DateTimeFormatter.ofPattern("MMyy", Locale.ENGLISH);

  @Override
  public JsonElement serialize(final YearMonth src, final Type typeOfSrc, final JsonSerializationContext context) {
    return new JsonPrimitive(toYearMonthString(src));
  }

  @Override
  public YearMonth deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
    return toYearMonth(json.getAsString());
  }

  public static String toYearMonthString(final YearMonth yearMonth) {
    return yearMonth.format(YEAR_MONTH_PATTERN);
  }

  public static YearMonth toYearMonth(final String yearMonthString) {
    return YearMonth.parse(yearMonthString, YEAR_MONTH_PATTERN);
  }
}
