package org.microworld.mangopay.tests;

import org.junit.Test;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.UserApi;
import org.microworld.mangopay.exceptions.MangopayUnauthorizedException;

public class ConnectIT extends AbstractIntegrationTest {
  @Test
  public void anExceptionIsThrownWhenConnectingToMangopayWithInvalidCredentials() {
    thrown.expect(MangopayUnauthorizedException.class);
    thrown.expectMessage("invalid_client: Authentication failed");
    final MangopayConnection connection = MangopayConnection.createDefault("api.sandbox.mangopay.com", "bar", "baz");
    UserApi.createDefault(connection).get("42");
  }
}
