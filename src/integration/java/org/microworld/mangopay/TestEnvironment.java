/**
 * Copyright © 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay;

public class TestEnvironment {
    public static final String SANDBOX_PASSPHRASE = "cqFfFrWfCcb7UadHNxx2C9Lo6Djw8ZduLi7J9USTmu8bhxxpju";
    public static final String SANDBOX_CLIENT_ID = "sdk-unit-tests";
    public static final String SANDBOX_HOST = "api.sandbox.mangopay.com";
    private static TestEnvironment testEnvironment;
    private final MangopayConnection connection;

    private TestEnvironment() {
        connection = MangopayConnection.createDefault(SANDBOX_HOST, SANDBOX_CLIENT_ID, SANDBOX_PASSPHRASE);
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
