package org.microworld.mangopay.implementation;

import java.util.List;

import org.microworld.mangopay.EventApi;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.Event;
import org.microworld.mangopay.search.Filter;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public class DefaultEventApi implements EventApi {
  private final MangopayConnection connection;

  public DefaultEventApi(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public List<Event> list(final Filter filter, final Sort sort, final Page page) {
    return connection.queryForList(Event.class, HttpMethod.GET, "/events", filter, sort, page);
  }
}
