package org.microworld.mangopay;

import java.util.List;

import org.microworld.mangopay.implementation.DefaultMangoPayConnection;
import org.microworld.mangopay.implementation.HttpMethod;

public interface MangoPayConnection {
  static MangoPayConnection createDefault(final String host, final String clientId, final String passphrase) {
    return new DefaultMangoPayConnection(host, clientId, passphrase);
  }

  <T> List<T> queryForList(final Class<T> type, final HttpMethod method, final String path, Filter filter, Sort sort, Page page);

  <T> T queryForObject(final Class<T> type, final HttpMethod method, final String path, final Object data, String... parameters);
}
