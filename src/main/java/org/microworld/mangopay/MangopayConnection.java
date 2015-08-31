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
package org.microworld.mangopay;

import java.util.List;

import org.microworld.mangopay.implementation.DefaultMangopayConnection;
import org.microworld.mangopay.misc.HttpMethod;
import org.microworld.mangopay.search.Filter;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public interface MangopayConnection {
  static MangopayConnection createDefault(final String host, final String clientId, final String passphrase) {
    return new DefaultMangopayConnection(host, clientId, passphrase);
  }

  <T> List<T> queryForList(final Class<T> type, final HttpMethod method, final String path, Filter filter, Sort sort, Page page, String... pathParameters);

  <T> T queryForObject(final Class<T> type, final HttpMethod method, final String path, final Object data, String... pathParameters);

  void query(HttpMethod method, String path, Object data, String... pathParameters);
}
