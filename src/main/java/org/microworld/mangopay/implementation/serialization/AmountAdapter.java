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

import static org.microworld.mangopay.misc.Predicates.not;

import java.lang.reflect.Type;
import java.util.Currency;
import java.util.Optional;

import org.microworld.mangopay.entities.Amount;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AmountAdapter implements JsonSerializer<Amount>, JsonDeserializer<Amount> {
  private static final Currency NO_CURRENCY = Currency.getInstance("XXX");
  private final CurrencyAdapter currencyAdapter = new CurrencyAdapter();

  @Override
  public JsonElement serialize(final Amount src, final Type typeOfSrc, final JsonSerializationContext context) {
    final JsonObject result = new JsonObject();
    result.add("Currency", currencyAdapter.serialize(src.getCurrency(), null, null));
    result.add("Amount", new JsonPrimitive(src.getCents()));
    return result;
  }

  @Override
  public Amount deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
    return Optional.of(json).filter(not(JsonElement::isJsonNull)).map(JsonElement::getAsJsonObject).map(this::toAmount).filter(this::hasCurrency).orElse(null);
  }

  private boolean hasCurrency(final Amount amount) {
    return !NO_CURRENCY.equals(amount.getCurrency());
  }

  private Amount toAmount(final JsonObject jsonObject) {
    return new Amount(currencyAdapter.deserialize(jsonObject.get("Currency"), null, null), jsonObject.get("Amount").getAsInt());
  }
}
