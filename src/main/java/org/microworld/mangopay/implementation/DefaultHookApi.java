package org.microworld.mangopay.implementation;

import java.util.List;

import org.microworld.mangopay.HookApi;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.Hook;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public class DefaultHookApi implements HookApi {
  private final MangopayConnection connection;

  public DefaultHookApi(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public Hook create(final Hook hook) {
    return connection.queryForObject(Hook.class, HttpMethod.POST, "/hooks", hook);
  }

  @Override
  public Hook get(final String id) {
    return connection.queryForObject(Hook.class, HttpMethod.GET, "/hooks/{0}", null, id);
  }

  @Override
  public Hook update(final Hook hook) {
    return connection.queryForObject(Hook.class, HttpMethod.PUT, "/hooks/{0}", hook, hook.getId());
  }

  @Override
  public List<Hook> list() {
    return connection.queryForList(Hook.class, HttpMethod.GET, "/hooks", null, Sort.byDefault(), Page.of(1, Page.MAX_PAGE_SIZE));
  }
}
