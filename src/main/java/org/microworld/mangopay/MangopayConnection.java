package org.microworld.mangopay;

import java.util.List;

import org.microworld.mangopay.implementation.DefaultMangopayConnection;
import org.microworld.mangopay.implementation.HttpMethod;
import org.microworld.mangopay.search.Filter;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public interface MangopayConnection {
  static MangopayConnection createDefault(final String host, final String clientId, final String passphrase) {
    return new DefaultMangopayConnection(host, clientId, passphrase);
  }

  <T> List<T> queryForList(final Class<T> type, final HttpMethod method, final String path, Filter filter, Sort sort, Page page);

  <T> T queryForObject(final Class<T> type, final HttpMethod method, final String path, final Object data, String... parameters);
}
