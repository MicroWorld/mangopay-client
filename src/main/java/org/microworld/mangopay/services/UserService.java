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
package org.microworld.mangopay.services;

import java.util.List;

import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

public interface UserService {
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