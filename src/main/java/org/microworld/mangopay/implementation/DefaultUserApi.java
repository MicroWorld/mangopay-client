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
package org.microworld.mangopay.implementation;

import java.util.List;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.UserApi;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.search.Filter;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public class DefaultUserApi implements UserApi {
  private final MangopayConnection connection;

  public DefaultUserApi(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public LegalUser create(final LegalUser user) {
    return connection.queryForObject(LegalUser.class, HttpMethod.POST, "/users/legal", user);
  }

  @Override
  public NaturalUser create(final NaturalUser user) {
    return connection.queryForObject(NaturalUser.class, HttpMethod.POST, "/users/natural", user);
  }

  @Override
  public User get(final String id) {
    return connection.queryForObject(User.class, HttpMethod.GET, "/users/{0}", null, id);
  }

  @Override
  public LegalUser getLegalUser(final String id) {
    return connection.queryForObject(LegalUser.class, HttpMethod.GET, "/users/legal/{0}", null, id);
  }

  @Override
  public NaturalUser getNaturalUser(final String id) {
    return connection.queryForObject(NaturalUser.class, HttpMethod.GET, "/users/natural/{0}", null, id);
  }

  @Override
  public LegalUser update(final LegalUser user) {
    return connection.queryForObject(LegalUser.class, HttpMethod.PUT, "/users/legal/{0}", user, user.getId());
  }

  @Override
  public NaturalUser update(final NaturalUser user) {
    return connection.queryForObject(NaturalUser.class, HttpMethod.PUT, "/users/natural/{0}", user, user.getId());
  }

  @Override
  public List<User> list(final Sort sort, final Page page) {
    return connection.queryForList(User.class, HttpMethod.GET, "/users", null, sort, page);
  }

  @Override
  public List<Wallet> getWallets(final String userId, final Sort sort, final Page page) {
    return connection.queryForList(Wallet.class, HttpMethod.GET, "/users/{0}/wallets", Filter.none(), sort, page, userId);
  }
}
