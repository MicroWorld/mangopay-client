package org.microworld.test;

import java.time.Instant;

import org.hamcrest.Matcher;
import org.microworld.test.matchers.IsAfter;
import org.microworld.test.matchers.IsAround;
import org.microworld.test.matchers.IsBefore;

public class Matchers {
  public static Matcher<Instant> after(final Instant instant) {
    return new IsAfter(instant);
  }

  public static Matcher<Instant> around(final Instant instant) {
    return new IsAround(instant);
  }

  public static Matcher<Instant> before(final Instant instant) {
    return new IsBefore(instant);
  }
}
