package org.microworld.mangopay.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.microworld.mangopay.Filter.afterDate;
import static org.microworld.mangopay.Filter.beforeDate;
import static org.microworld.mangopay.Filter.eventType;
import static org.microworld.mangopay.SortDirection.ASCENDING;
import static org.microworld.mangopay.SortField.EVENT_DATE;
import static org.microworld.mangopay.entities.EventType.PAYIN_NORMAL_SUCCEEDED;
import static org.microworld.test.Matchers.after;
import static org.microworld.test.Matchers.before;

import java.time.Instant;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.microworld.mangopay.EventApi;
import org.microworld.mangopay.Filter;
import org.microworld.mangopay.Page;
import org.microworld.mangopay.Sort;
import org.microworld.mangopay.entities.Event;

public class EventIT extends AbstractIntegrationTest {
  private EventApi eventApi;

  @Before
  public void setUpEventApi() {
    eventApi = EventApi.createDefault(connection);
  }

  @Test
  public void listFirstPage() {
    final List<Event> events = eventApi.list(Filter.none(), Sort.byDefault(), Page.of(1));
    assertThat(events, hasSize(10));
  }

  @Test
  public void listWithFilters() {
    final Instant from = Instant.parse("2014-07-01T00:00:00Z");
    final Instant to = Instant.parse("2015-01-01T00:00:00Z");
    final List<Event> events = eventApi.list(afterDate(from).and(beforeDate(to)).and(eventType(PAYIN_NORMAL_SUCCEEDED)), Sort.by(EVENT_DATE, ASCENDING), Page.of(1));
    events.forEach(e -> {
      assertThat(e.getDate(), is(both(after(from)).and(before(to))));
      assertThat(e.getType(), is(equalTo(PAYIN_NORMAL_SUCCEEDED)));
    });
    assertThat(events, hasSize(10));
    assertThat(events.get(0).getDate(), is(before(events.get(9).getDate())));
  }
}
