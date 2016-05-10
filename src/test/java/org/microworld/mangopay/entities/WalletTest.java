/**
 * Copyright (C) 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
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

import java.util.Currency;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.codearte.jfairy.Fairy;

public class WalletTest {
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void ownerIdIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Owner ID must not be null.");
    new Wallet(null, Currency.getInstance("EUR"), "", "");
  }

  @Test
  public void currencyIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Currency must not be null.");
    new Wallet("", null, "", "");
  }

  @Test
  public void descriptionIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Description must not be null.");
    new Wallet("", Currency.getInstance("EUR"), null, "");
  }

  @Test
  public void tagIsNotMandatory() {
    Wallet wallet = new Wallet("", Currency.getInstance("EUR"), "", null);
    assertThat(wallet.getTag(), is(equalTo(null)));

    final String tag = Fairy.create().textProducer().word();
    wallet = new Wallet("", Currency.getInstance("EUR"), "", tag);
    assertThat(wallet.getTag(), is(equalTo(tag)));
  }

  @Test
  public void setDescription() {
    final Wallet wallet = new Wallet("", Currency.getInstance("EUR"), "foo", null);

    final String description = Fairy.create().textProducer().word();
    wallet.setDescription(description);
    assertThat(wallet.getDescription(), is(equalTo(description)));
  }

  @Test
  public void setDescriptionDoesNotAllowNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Description must not be null.");
    new Wallet("", Currency.getInstance("EUR"), "foo", null).setDescription(null);
  }
}
