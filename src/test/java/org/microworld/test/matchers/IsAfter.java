package org.microworld.test.matchers;

import java.time.Instant;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsAfter extends TypeSafeDiagnosingMatcher<Instant> {
  private final Instant instant;

  public IsAfter(final Instant instant) {
    this.instant = instant;
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText("after ").appendValue(instant);
  }

  @Override
  protected boolean matchesSafely(final Instant item, final Description mismatchDescription) {
    if (instant.isAfter(item)) {
      mismatchDescription.appendText("was ").appendValue(item);
      return false;
    }
    return true;
  }
}
