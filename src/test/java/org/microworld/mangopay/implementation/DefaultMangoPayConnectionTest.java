package org.microworld.mangopay.implementation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.microworld.mangopay.MangoPayConnection;

public class DefaultMangoPayConnectionTest {
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void contructorThrowsExceptionIfHostIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("host");
    new DefaultMangoPayConnection(null, "foo", "bar");
  }

  @Test
  public void contructorThrowsExceptionIfClientIdIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("clientId");
    new DefaultMangoPayConnection("foo", null, "bar");
  }

  @Test
  public void contructorThrowsExceptionIfPassphraseIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("passphrase");
    new DefaultMangoPayConnection("foo", "bar", null);
  }

  @Test
  public void encodedAuthenticationStringIsBase64EncodedClientIdAndPassphrase() throws Exception {
    final MangoPayConnection connection = new DefaultMangoPayConnection("foo", "Aladdin", "open sesame");
    assertThat(getEncodedAuthenticationString(connection), is(equalTo("QWxhZGRpbjpvcGVuIHNlc2FtZQ==")));
  }

  private String getEncodedAuthenticationString(final MangoPayConnection connection) throws Exception {
    final Field f = DefaultMangoPayConnection.class.getDeclaredField("encodedAuthenticationString");
    f.setAccessible(true);
    return (String) f.get(connection);
  }
}
