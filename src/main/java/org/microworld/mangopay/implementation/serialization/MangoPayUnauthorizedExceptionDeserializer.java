package org.microworld.mangopay.implementation.serialization;

import java.lang.reflect.Type;

import org.microworld.mangopay.exceptions.MangoPayUnauthorizedException;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class MangoPayUnauthorizedExceptionDeserializer implements JsonDeserializer<MangoPayUnauthorizedException> {
  @Override
  public MangoPayUnauthorizedException deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
    final JsonObject object = json.getAsJsonObject();
    return new MangoPayUnauthorizedException(object.get("error").getAsString(), object.get("error_description").getAsString());
  }
}
