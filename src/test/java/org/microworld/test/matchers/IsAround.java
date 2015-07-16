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
    if (instant.minusSeconds(5).isAfter(item) || instant.plusSeconds(10).isBefore(item)) {
      mismatchDescription.appendText("was ").appendValue(item);
      return false;
    }
    return true;
  }
}
