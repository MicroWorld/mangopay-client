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
package org.microworld.mangopay.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.microworld.mangopay.entities.EventType.PAYIN_NORMAL_SUCCEEDED;
import static org.microworld.mangopay.search.Filter.afterDate;
import static org.microworld.mangopay.search.Filter.beforeDate;
import static org.microworld.mangopay.search.Filter.eventType;
import static org.microworld.mangopay.search.SortDirection.ASCENDING;
import static org.microworld.mangopay.search.SortField.EVENT_DATE;
import static org.microworld.test.Matchers.after;
import static org.microworld.test.Matchers.before;

import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.microworld.mangopay.entities.Event;
import org.microworld.mangopay.search.Filter;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public class EventIT extends AbstractIntegrationTest {
  @Test
  public void listFirstPage() {
    final List<Event> events = client.getEventService().list(Filter.none(), Sort.byDefault(), Page.of(1));
    assertThat(events, hasSize(10));
  }

  @Test
  public void listWithFilters() {
    final Instant from = Instant.parse("2014-07-01T00:00:00Z");
    final Instant to = Instant.parse("2015-01-01T00:00:00Z");
    final List<Event> events = client.getEventService().list(afterDate(from).and(beforeDate(to)).and(eventType(PAYIN_NORMAL_SUCCEEDED)), Sort.by(EVENT_DATE, ASCENDING), Page.of(1));
    events.forEach(e -> {
      assertThat(e.getDate(), is(both(after(from)).and(before(to))));
      assertThat(e.getType(), is(equalTo(PAYIN_NORMAL_SUCCEEDED)));
    });
    assertThat(events, hasSize(10));
    assertThat(events.get(0).getDate(), is(before(events.get(9).getDate())));
  }
}
