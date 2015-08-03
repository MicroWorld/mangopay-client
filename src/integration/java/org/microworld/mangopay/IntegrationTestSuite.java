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
package org.microworld.mangopay;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.microworld.mangopay.tests.ConnectIT;
import org.microworld.mangopay.tests.EventIT;
import org.microworld.mangopay.tests.HookIT;
import org.microworld.mangopay.tests.UserIT;
import org.microworld.mangopay.tests.WalletIT;

@RunWith(Suite.class)
@SuiteClasses({
    ConnectIT.class,
    UserIT.class,
    EventIT.class,
    HookIT.class,
    WalletIT.class,
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
