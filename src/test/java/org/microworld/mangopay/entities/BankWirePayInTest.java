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

public class BankWirePayInTest {
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void authorIdIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Author ID must not be null.");
    new BankWirePayIn(null, "", "", Currency.getInstance("EUR"), 0, 0, "");
  }

  @Test
  public void creditedUserIdIsNotMandatory() {
    BankWirePayIn bankWirePayIn = new BankWirePayIn("", null, "", Currency.getInstance("EUR"), 0, 0, "");
    assertThat(bankWirePayIn.getCreditedUserId(), is(equalTo(null)));

    final String creditedUserId = Fairy.create().textProducer().word();
    bankWirePayIn = new BankWirePayIn("", creditedUserId, "", Currency.getInstance("EUR"), 0, 0, "");
    assertThat(bankWirePayIn.getCreditedUserId(), is(equalTo(creditedUserId)));
  }

  @Test
  public void creditedWalletIdIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Credited wallet ID must not be null.");
    new BankWirePayIn("", "", null, Currency.getInstance("EUR"), 0, 0, "");
  }

  @Test
  public void currencyIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Currency must not be null.");
    new BankWirePayIn("", "", "", null, 0, 0, "");
  }

  @Test
  public void tagIsNotMandatory() {
    BankWirePayIn bankWirePayIn = new BankWirePayIn("", "", "", Currency.getInstance("EUR"), 0, 0, null);
    assertThat(bankWirePayIn.getTag(), is(equalTo(null)));

    final String tag = Fairy.create().textProducer().word();
    bankWirePayIn = new BankWirePayIn("", "", "", Currency.getInstance("EUR"), 0, 0, tag);
    assertThat(bankWirePayIn.getTag(), is(equalTo(tag)));
  }
}
