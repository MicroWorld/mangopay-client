package org.microworld.mangopay.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.TestEnvironment;

public class AbstractIntegrationTest {
  @Rule
  public final ExpectedException thrown = ExpectedException.none();
  protected MangopayConnection connection;

  @Before
  public void setUpConnection() {
    connection = TestEnvironment.getInstance().getConnection();
  }
}
