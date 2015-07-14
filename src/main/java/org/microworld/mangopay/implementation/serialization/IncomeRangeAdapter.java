package org.microworld.mangopay.implementation.serialization;

import java.lang.reflect.Type;

import org.microworld.mangopay.entities.IncomeRange;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class IncomeRangeAdapter implements JsonSerializer<IncomeRange>, JsonDeserializer<IncomeRange> {
  @Override
  public IncomeRange deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
    return IncomeRange.parse(json.getAsString());
  }

  @Override
  public JsonElement serialize(final IncomeRange src, final Type typeOfSrc, final JsonSerializationContext context) {
    return new JsonPrimitive(src.getValue());
  }
}
