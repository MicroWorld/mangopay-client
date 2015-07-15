package org.microworld.mangopay;

public class TestEnvironment {
  private static TestEnvironment testEnvironment;
  private final MangopayConnection connection;

  private TestEnvironment() {
    final String host = "api.sandbox.mangopay.com";
    final String clientId = "sdk-unit-tests";
    final String passphrase = "cqFfFrWfCcb7UadHNxx2C9Lo6Djw8ZduLi7J9USTmu8bhxxpju";
    connection = MangopayConnection.createDefault(host, clientId, passphrase);
  }

  public static TestEnvironment getInstance() {
    if (testEnvironment == null) {
      testEnvironment = new TestEnvironment();
    }
    return testEnvironment;
  }

  public MangopayConnection getConnection() {
    return connection;
  }
}
