package org.microworld.mangopay;

import java.util.List;

import org.microworld.mangopay.entities.Event;
import org.microworld.mangopay.implementation.DefaultEventApi;

public interface EventApi {
  static EventApi createDefault(final MangoPayConnection connection) {
    return new DefaultEventApi(connection);
  }

  List<Event> list(Filter filter, Sort sort, Page page);
}
