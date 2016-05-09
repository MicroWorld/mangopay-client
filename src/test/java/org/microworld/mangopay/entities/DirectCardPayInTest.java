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

public class DirectCardPayInTest {
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void authorIdIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Author ID must not be null.");
    new DirectCardPayIn(null, "", "", "", Currency.getInstance("EUR"), 0, 0, "", SecureMode.DEFAULT, null);
  }

  @Test
  public void creditedUserIdIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Credited user ID must not be null.");
    new DirectCardPayIn("", null, "", "", Currency.getInstance("EUR"), 0, 0, "", SecureMode.DEFAULT, null);
  }

  @Test
  public void creditedWalletIdIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Credited wallet ID must not be null.");
    new DirectCardPayIn("", "", null, "", Currency.getInstance("EUR"), 0, 0, "", SecureMode.DEFAULT, null);
  }

  @Test
  public void cardIdIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Card ID must not be null.");
    new DirectCardPayIn("", "", "", null, Currency.getInstance("EUR"), 0, 0, "", SecureMode.DEFAULT, null);
  }

  @Test
  public void currencyIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Currency must not be null.");
    new DirectCardPayIn("", "", "", "", null, 0, 0, "", SecureMode.DEFAULT, null);
  }

  @Test
  public void secureModeReturnUrlIsMandatory() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Secure mode return URL must not be null.");
    new DirectCardPayIn("", "", "", "", Currency.getInstance("EUR"), 0, 0, null, SecureMode.DEFAULT, null);
  }

  @Test
  public void secureModeIsNotMandatory() {
    DirectCardPayIn directCardPayIn = new DirectCardPayIn("", "", "", "", Currency.getInstance("EUR"), 0, 0, "", null, "");
    assertThat(directCardPayIn.getSecureMode(), is(equalTo(null)));

    final SecureMode secureMode = Fairy.create().baseProducer().randomElement(SecureMode.values());
    directCardPayIn = new DirectCardPayIn("", "", "", "", Currency.getInstance("EUR"), 0, 0, "", secureMode, "");
    assertThat(directCardPayIn.getSecureMode(), is(equalTo(secureMode)));
  }

  @Test
  public void tagIsNotMandatory() {
    DirectCardPayIn directCardPayIn = new DirectCardPayIn("", "", "", "", Currency.getInstance("EUR"), 0, 0, "", SecureMode.DEFAULT, null);
    assertThat(directCardPayIn.getTag(), is(equalTo(null)));

    final String tag = Fairy.create().textProducer().word();
    directCardPayIn = new DirectCardPayIn("", "", "", "", Currency.getInstance("EUR"), 0, 0, "", SecureMode.DEFAULT, tag);
    assertThat(directCardPayIn.getTag(), is(equalTo(tag)));
  }
}
