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
package org.microworld.test.matchers;

import java.time.Instant;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsAround extends TypeSafeDiagnosingMatcher<Instant> {
  private final Instant instant;

  public IsAround(final Instant instant) {
    this.instant = instant;
  }

  @Override
  public void describeTo(final Description description) {
    description.appendValue(instant);
  }

  @Override
  protected boolean matchesSafely(final Instant item, final Description mismatchDescription) {
    if (instant.minusSeconds(10).isAfter(item) || instant.plusSeconds(10).isBefore(item)) {
      mismatchDescription.appendText("was ").appendValue(item);
      return false;
    }
    return true;
  }
}
