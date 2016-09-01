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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.microworld.mangopay.misc.Reflections.setFieldValue;
import static org.microworld.test.Matchers.token;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TokenTest {
  private Gson gson;

  @Before
  public void setUpGson() {
    gson = new GsonBuilder().create();
  }

  @Test
  public void deserialization() {
    final String json = "{\"access_token\":\"67b036bd007c40378d4be5a934f197e6\",\"token_type\":\"Bearer\",\"expires_in\":3600}";
    assertThat(gson.fromJson(json, Token.class), is(token("67b036bd007c40378d4be5a934f197e6", "Bearer", 3600)));
  }

  @Test
  public void isExpired() {
    assertThat(createToken("foo", "bar", 3600, System.nanoTime()).isExpired(), is(false));
    assertThat(createToken("foo", "bar", 3600, System.nanoTime() - 3590L * 1000000000L).isExpired(), is(false));
    assertThat(createToken("foo", "bar", 3600, System.nanoTime() - 3600L * 1000000000L).isExpired(), is(true));
    assertThat(createToken("foo", "bar", 3600, System.nanoTime() - 4000L * 1000000000L).isExpired(), is(true));
  }

  @Test
  public void headerValue() {
    assertThat(createToken("token-value", "token-type", 1, 1).getHeaderValue(), is(equalTo("token-type token-value")));
  }

  private Token createToken(final String value, final String type, final int duration, final long creationTime) {
    final Token token = new Token();
    setFieldValue(Token.class, "value", token, value);
    setFieldValue(Token.class, "type", token, type);
    setFieldValue(Token.class, "duration", token, duration);
    setFieldValue(Token.class, "creationTime", token, creationTime);
    return token;
  }
}
