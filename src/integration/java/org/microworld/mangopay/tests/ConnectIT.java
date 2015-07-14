package org.microworld.mangopay.tests;

import org.junit.Test;
import org.microworld.mangopay.MangoPayConnection;
import org.microworld.mangopay.UserApi;
import org.microworld.mangopay.exceptions.MangoPayUnauthorizedException;

public class ConnectIT extends AbstractIntegrationTest {
  @Test
  public void anExceptionIsThrownWhenConnectingToMangoPayWithInvalidCredentials() {
    thrown.expect(MangoPayUnauthorizedException.class);
    thrown.expectMessage("invalid_client: Authentication failed");
    final MangoPayConnection connection = MangoPayConnection.createDefault("api.sandbox.mangopay.com", "bar", "baz");
    UserApi.createDefault(connection).get("42");
  }
}
