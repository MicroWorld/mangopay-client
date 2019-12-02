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
package org.microworld.mangopay.tests;

import io.codearte.jfairy.producer.person.Person;
import org.junit.Test;
import org.microworld.mangopay.MangopayClient;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.RateLimit;
import org.microworld.mangopay.exceptions.MangopayUnauthorizedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.microworld.mangopay.TestEnvironment.SANDBOX_CLIENT_ID;
import static org.microworld.mangopay.TestEnvironment.SANDBOX_HOST;
import static org.microworld.mangopay.TestEnvironment.SANDBOX_PASSPHRASE;
import static org.microworld.mangopay.entities.RateLimitInterval._15_MINUTES;
import static org.microworld.mangopay.entities.RateLimitInterval._1_DAY;
import static org.microworld.mangopay.entities.RateLimitInterval._1_HOUR;
import static org.microworld.mangopay.entities.RateLimitInterval._30_MINUTES;

public class ConnectIT extends AbstractIntegrationTest {
    @Test
    public void anExceptionIsThrownWhenConnectingToMangopayWithInvalidCredentials() {
        final Person client = fairy.person();
        thrown.expect(MangopayUnauthorizedException.class);
        thrown.expectMessage("invalid_client: (no error description)");
        final MangopayConnection connection = MangopayConnection.createDefault(SANDBOX_HOST, client.getUsername(), client.getPassword());
        MangopayClient.createDefault(connection).getUserService().get("42");
    }

    @Test
    public void connectedClientProvidesRateLimitInformation() {
        final MangopayConnection connection = MangopayConnection.createDefault(SANDBOX_HOST, SANDBOX_CLIENT_ID, SANDBOX_PASSPHRASE);
        assertThat(connection.getRateLimit(_15_MINUTES), is(notNullValue()));
        assertThat(connection.getRateLimit(_30_MINUTES), is(notNullValue()));
        assertThat(connection.getRateLimit(_1_HOUR), is(notNullValue()));
        assertThat(connection.getRateLimit(_1_DAY), is(notNullValue()));

        assertThat(total(connection.getRateLimit(_15_MINUTES)), is(lessThan(total(connection.getRateLimit(_30_MINUTES)))));
        assertThat(total(connection.getRateLimit(_30_MINUTES)), is(lessThan(total(connection.getRateLimit(_1_HOUR)))));
        assertThat(total(connection.getRateLimit(_1_HOUR)), is(lessThan(total(connection.getRateLimit(_1_DAY)))));
    }

    private int total(final RateLimit rateLimit) {
        return rateLimit.getCallsMade() + rateLimit.getCallsRemaining();
    }
}
