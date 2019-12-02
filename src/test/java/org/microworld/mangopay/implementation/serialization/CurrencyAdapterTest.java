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

import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;

import java.util.Currency;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CurrencyAdapterTest {
    private CurrencyAdapter adapter;

    @Before
    public void setUpAdapter() {
        adapter = new CurrencyAdapter();
    }

    @Test
    public void serialize() {
        assertThat(adapter.serialize(Currency.getInstance("EUR"), null, null).toString(), is(equalTo("\"EUR\"")));
        assertThat(adapter.serialize(Currency.getInstance("USD"), null, null).toString(), is(equalTo("\"USD\"")));
    }

    @Test
    public void deserialize() {
        assertThat(adapter.deserialize(JsonParser.parseString("\"EUR\""), null, null), is(equalTo(Currency.getInstance("EUR"))));
        assertThat(adapter.deserialize(JsonParser.parseString("\"USD\""), null, null), is(equalTo(Currency.getInstance("USD"))));
    }
}
