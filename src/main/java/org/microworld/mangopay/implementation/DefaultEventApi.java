package org.microworld.mangopay.implementation;

import java.util.List;

import org.microworld.mangopay.EventApi;
import org.microworld.mangopay.Filter;
import org.microworld.mangopay.MangoPayConnection;
import org.microworld.mangopay.Page;
import org.microworld.mangopay.Sort;
import org.microworld.mangopay.entities.Event;

public class DefaultEventApi implements EventApi {
  private final MangoPayConnection connection;

  public DefaultEventApi(final MangoPayConnection connection) {
    this.connection = connection;
  }

  @Override
  public List<Event> list(final Filter filter, final Sort sort, final Page page) {
    return connection.queryForList(Event.class, HttpMethod.GET, "/events", filter, sort, page);
  }
}
