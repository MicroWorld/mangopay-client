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
package org.microworld.mangopay.implementation;

import static java.time.Instant.ofEpochSecond;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.microworld.mangopay.entities.RateLimitInterval._15_MINUTES;
import static org.microworld.mangopay.entities.RateLimitInterval._1_DAY;
import static org.microworld.mangopay.entities.RateLimitInterval._1_HOUR;
import static org.microworld.mangopay.entities.RateLimitInterval._30_MINUTES;
import static org.microworld.test.Matchers.rateLimit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.microworld.mangopay.entities.RateLimit;
import org.microworld.mangopay.entities.RateLimitInterval;

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
  public void encodedAuthenticationStringReturnsBase64EncodedClientIdAndPassphrase() {
    assertThat(DefaultMangopayConnection.encodeAuthenticationString("Aladdin", "open sesame"), is(equalTo("QWxhZGRpbjpvcGVuIHNlc2FtZQ==")));
  }

  @Test
  public void parseRateLimits() {
    final Map<RateLimitInterval, RateLimit> limits = new HashMap<>();
    final Map<String, List<String>> headers = new HashMap<>();
    headers.put("X-RateLimit", asList("4", "3", "2", "1"));
    headers.put("X-RateLimit-Remaining", asList("40", "30", "20", "10"));
    headers.put("X-RateLimit-Reset", asList("1500086400", "1500003600", "1500001800", "1500000000"));

    DefaultMangopayConnection.parseRateLimits(limits, headers);

    assertThat(limits, hasEntry(equalTo(_15_MINUTES), rateLimit(1, 10, ofEpochSecond(1500000000))));
    assertThat(limits, hasEntry(equalTo(_30_MINUTES), rateLimit(2, 20, ofEpochSecond(1500001800))));
    assertThat(limits, hasEntry(equalTo(_1_HOUR), rateLimit(3, 30, ofEpochSecond(1500003600))));
    assertThat(limits, hasEntry(equalTo(_1_DAY), rateLimit(4, 40, ofEpochSecond(1500086400))));
  }

  @Test
  public void parseRateLimitsDoesNothingIfHeadersMissing() {
    final Map<RateLimitInterval, RateLimit> limits = new HashMap<>();
    final Map<String, List<String>> headers = new HashMap<>();

    DefaultMangopayConnection.parseRateLimits(limits, headers);

    assertThat(limits, is(anEmptyMap()));
  }

  @Test
  public void parseRateLimitsDoesNothingIfHeadersDoNotHaveEnoughContent() {
    final Map<RateLimitInterval, RateLimit> limits = new HashMap<>();
    final Map<String, List<String>> headers = new HashMap<>();
    headers.put("X-RateLimit", asList("1", "2", "3"));
    headers.put("X-RateLimit-Remaining", asList("10", "20", "30"));
    headers.put("X-RateLimit-Reset", asList("1500000000", "1500001800", "1500003600"));

    DefaultMangopayConnection.parseRateLimits(limits, headers);

    assertThat(limits, is(anEmptyMap()));
  }
}
