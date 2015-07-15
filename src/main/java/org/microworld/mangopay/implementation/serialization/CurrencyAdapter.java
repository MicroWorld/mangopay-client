package org.microworld.mangopay.implementation.serialization;

import java.lang.reflect.Type;
import java.util.Currency;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CurrencyAdapter implements JsonSerializer<Currency>, JsonDeserializer<Currency> {
  @Override
  public JsonElement serialize(final Currency src, final Type typeOfSrc, final JsonSerializationContext context) {
    return new JsonPrimitive(src.getCurrencyCode());
  }

  @Override
  public Currency deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
    return Currency.getInstance(json.getAsString());
  }
}
