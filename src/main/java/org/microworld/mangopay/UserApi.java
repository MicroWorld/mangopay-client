package org.microworld.mangopay;

import java.util.List;

import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.implementation.DefaultUserApi;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public interface UserApi {
  static UserApi createDefault(final MangopayConnection connection) {
    return new DefaultUserApi(connection);
  }

  LegalUser create(final LegalUser user);

  NaturalUser create(final NaturalUser user);

  User get(String id);

  LegalUser getLegalUser(String id);

  NaturalUser getNaturalUser(String id);

  LegalUser update(LegalUser user);

  NaturalUser update(NaturalUser user);

  List<User> list(Sort sort, Page page);

  List<Wallet> getWallets(String userId, Sort sort, Page page);
}
