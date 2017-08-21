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
