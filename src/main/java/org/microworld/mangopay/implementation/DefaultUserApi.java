package org.microworld.mangopay.implementation;

import java.util.List;

import org.microworld.mangopay.MangoPayConnection;
import org.microworld.mangopay.Page;
import org.microworld.mangopay.Sort;
import org.microworld.mangopay.UserApi;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.User;

public class DefaultUserApi implements UserApi {
  private final MangoPayConnection connection;

  public DefaultUserApi(final MangoPayConnection connection) {
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
}
