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
package org.microworld.mangopay.implementation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.microworld.mangopay.MangopayConnection;

public class DefaultMangopayConnectionTest {
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void contructorThrowsExceptionIfHostIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("The host must not be null.");
    new DefaultMangopayConnection(null, "foo", "bar");
  }

  @Test
  public void contructorThrowsExceptionIfClientIdIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("The clientId must not be null.");
    new DefaultMangopayConnection("foo", null, "bar");
  }

  @Test
  public void contructorThrowsExceptionIfPassphraseIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("The passphrase must not be null.");
    new DefaultMangopayConnection("foo", "bar", null);
  }

  @Test
  public void encodedAuthenticationStringIsBase64EncodedClientIdAndPassphrase() throws Exception {
    final MangopayConnection connection = new DefaultMangopayConnection("foo", "Aladdin", "open sesame");
    assertThat(getEncodedAuthenticationString(connection), is(equalTo("QWxhZGRpbjpvcGVuIHNlc2FtZQ==")));
  }

  private String getEncodedAuthenticationString(final MangopayConnection connection) throws Exception {
    final Field f = DefaultMangopayConnection.class.getDeclaredField("encodedAuthenticationString");
    f.setAccessible(true);
    return (String) f.get(connection);
  }
}
