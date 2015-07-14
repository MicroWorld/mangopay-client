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
