package org.microworld.mangopay;

import java.util.List;

import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.implementation.DefaultUserApi;

public interface UserApi {
  static UserApi createDefault(final MangoPayConnection connection) {
    return new DefaultUserApi(connection);
  }

  LegalUser create(final LegalUser user);

  NaturalUser create(final NaturalUser user);

  User get(String id);

  LegalUser getLegalUser(String id);

  NaturalUser getNaturalUser(String id);

  LegalUser update(LegalUser fetchedUser);

  NaturalUser update(NaturalUser fetchedUser);

  List<User> list(Sort sort, Page page);
}
