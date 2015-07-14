package org.microworld.mangopay;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.microworld.mangopay.tests.ConnectIT;
import org.microworld.mangopay.tests.UserIT;

@RunWith(Suite.class)
@SuiteClasses({
    ConnectIT.class,
    UserIT.class,
})
public class IntegrationTestSuite {
  @ClassRule
  public static ExternalResource resource = new ExternalResource() {
    @Override
    protected void before() throws Throwable {
      TestEnvironment.getInstance();
    };
  };
}
