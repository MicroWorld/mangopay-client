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
package org.microworld.mangopay.entities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class RateLimitIntervalTest {
  @Test
  public void toStringReturnsHumanReadableDuration() {
    assertThat(RateLimitInterval._15_MINUTES.toString(), is(equalTo("00:15")));
    assertThat(RateLimitInterval._30_MINUTES.toString(), is(equalTo("00:30")));
    assertThat(RateLimitInterval._1_HOUR.toString(), is(equalTo("01:00")));
    assertThat(RateLimitInterval._1_DAY.toString(), is(equalTo("24:00")));
  }
}
