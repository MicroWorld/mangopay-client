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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.microworld.mangopay.exceptions.MangopayUnauthorizedException;

import java.lang.reflect.Type;
import java.util.Optional;

public class MangopayUnauthorizedExceptionDeserializer implements JsonDeserializer<MangopayUnauthorizedException> {
    @Override
    public MangopayUnauthorizedException deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
        final JsonObject object = json.getAsJsonObject();
        return new MangopayUnauthorizedException(object.get("error").getAsString(), Optional.ofNullable(object.get("error_description")).map(JsonElement::getAsString).orElse("(no error description)"));
    }
}
