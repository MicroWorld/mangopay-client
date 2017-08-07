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
package org.microworld.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;
import org.microworld.mangopay.entities.RateLimit;

public class RateLimitMatcher extends TypeSafeDiagnosingMatcher<RateLimit> {
  private final RateLimit rateLimit;

  public RateLimitMatcher(final RateLimit rateLimit) {
    this.rateLimit = rateLimit;
  }

  @Override
  public void describeTo(final Description description) {
    description.appendValue(rateLimit);
  }

  @Override
  protected boolean matchesSafely(final RateLimit actual, final Description mismatchDescription) {
    if (rateLimit.getClass().isInstance(actual) && isEqualTo(actual)) {
      return true;
    }
    mismatchDescription.appendValue(actual);
    return false;
  }

  private boolean isEqualTo(final RateLimit actual) {
    if (!IsEqual.equalTo(rateLimit.getCallsMade()).matches(actual.getCallsMade())) {
      return false;
    }
    if (!IsEqual.equalTo(rateLimit.getCallsRemaining()).matches(actual.getCallsRemaining())) {
      return false;
    }
    if (!IsEqual.equalTo(rateLimit.getReset()).matches(actual.getReset())) {
      return false;
    }
    return true;
  }
}
