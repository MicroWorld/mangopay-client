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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.microworld.mangopay.entities.Amount;

import com.google.gson.JsonNull;
import com.google.gson.JsonParser;

public class AmountAdapterTest {
  private AmountAdapter adapter;

  @Before
  public void setUpAdater() {
    adapter = new AmountAdapter();
  }

  @Test
  public void serialize() {
    assertThat(adapter.serialize(new Amount(Currency.getInstance("EUR"), -1337), null, null).toString(), is(equalTo("{\"Currency\":\"EUR\",\"Amount\":-1337}")));
    assertThat(adapter.serialize(new Amount(Currency.getInstance("EUR"), 0), null, null).toString(), is(equalTo("{\"Currency\":\"EUR\",\"Amount\":0}")));
    assertThat(adapter.serialize(new Amount(Currency.getInstance("EUR"), 1337), null, null).toString(), is(equalTo("{\"Currency\":\"EUR\",\"Amount\":1337}")));

    assertThat(adapter.serialize(new Amount(Currency.getInstance("USD"), -1337), null, null).toString(), is(equalTo("{\"Currency\":\"USD\",\"Amount\":-1337}")));
    assertThat(adapter.serialize(new Amount(Currency.getInstance("USD"), 0), null, null).toString(), is(equalTo("{\"Currency\":\"USD\",\"Amount\":0}")));
    assertThat(adapter.serialize(new Amount(Currency.getInstance("USD"), 1337), null, null).toString(), is(equalTo("{\"Currency\":\"USD\",\"Amount\":1337}")));
  }

  @Test
  public void deserialize() {
    assertThat(adapter.deserialize(JsonParser.parseString("{\"Currency\": \"EUR\",\"Amount\": -1337}"), null, null), is(equalTo(new Amount(Currency.getInstance("EUR"), -1337))));
    assertThat(adapter.deserialize(JsonParser.parseString("{\"Currency\": \"EUR\",\"Amount\": 0}"), null, null), is(equalTo(new Amount(Currency.getInstance("EUR"), 0))));
    assertThat(adapter.deserialize(JsonParser.parseString("{\"Currency\": \"EUR\",\"Amount\": 1337}"), null, null), is(equalTo(new Amount(Currency.getInstance("EUR"), 1337))));

    assertThat(adapter.deserialize(JsonParser.parseString("{\"Currency\": \"USD\",\"Amount\": -1337}"), null, null), is(equalTo(new Amount(Currency.getInstance("USD"), -1337))));
    assertThat(adapter.deserialize(JsonParser.parseString("{\"Currency\": \"USD\",\"Amount\": 0}"), null, null), is(equalTo(new Amount(Currency.getInstance("USD"), 0))));
    assertThat(adapter.deserialize(JsonParser.parseString("{\"Currency\": \"USD\",\"Amount\": 1337}"), null, null), is(equalTo(new Amount(Currency.getInstance("USD"), 1337))));

    assertThat(adapter.deserialize(JsonParser.parseString("{\"Currency\": \"XXX\",\"Amount\": -1337}"), null, null), is(nullValue()));
    assertThat(adapter.deserialize(JsonParser.parseString("{\"Currency\": \"XXX\",\"Amount\": 0}"), null, null), is(nullValue()));
    assertThat(adapter.deserialize(JsonParser.parseString("{\"Currency\": \"XXX\",\"Amount\": 1337}"), null, null), is(nullValue()));

    assertThat(adapter.deserialize(JsonNull.INSTANCE, null, null), is(nullValue()));
  }
}
