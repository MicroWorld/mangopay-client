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

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.microworld.mangopay.entities.Token;

public class TokenMatcher extends TypeSafeDiagnosingMatcher<Token> {
  private final int duration;
  private final String type;
  private final String value;

  public TokenMatcher(final int duration, final String type, final String value) {
    this.duration = duration;
    this.type = type;
    this.value = value;
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText("token ").appendValue(value).appendText(" of type ").appendValue(type).appendText(" valid for ").appendText(String.valueOf(duration)).appendText("s");
  }

  @Override
  protected boolean matchesSafely(final Token token, final Description mismatchDescription) {
    if (!value.equals(token.getValue()) || !type.equals(token.getType()) || duration != token.getDuration()) {
      mismatchDescription.appendText("token ").appendValue(token.getValue()).appendText(" of type ").appendValue(token.getType()).appendText(" valid for ").appendText(String.valueOf(token.getDuration())).appendText("s");
      return false;
    }
    if (token.getCreationTime() > System.nanoTime() || token.getCreationTime() < System.nanoTime() - 100000000) { // Check creation time is less than 100 ms ago
      mismatchDescription.appendText("token with invalid creation time ").appendText(String.valueOf(token.getCreationTime())).appendText("ns");
      return false;
    }
    return true;
  }
}
