/**
 * Copyright © 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.implementation.services;

import java.util.List;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;
import org.microworld.mangopay.misc.HttpMethod;
import org.microworld.mangopay.search.Filter;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;
import org.microworld.mangopay.services.BankAccountService;

public class DefaultBankAccountService implements BankAccountService {
  private final MangopayConnection connection;

  public DefaultBankAccountService(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public BankAccount create(final String userId, final BankAccount bankAccount) {
    return connection.queryForObject(BankAccount.class, HttpMethod.POST, "/users/{0}/bankaccounts/{1}", bankAccount, userId, bankAccount.getType().name());
  }

  @Override
  public BankAccount deactivate(final String userId, final String bankAccountId) {
    return connection.queryForObject(BankAccount.class, HttpMethod.PUT, "/users/{0}/bankaccounts/{1}", "{\"Active\":false}", userId, bankAccountId);
  }

  @Override
  public BankAccount get(final String userId, final String bankAccountId) {
    return connection.queryForObject(BankAccount.class, HttpMethod.GET, "/users/{0}/bankaccounts/{1}", null, userId, bankAccountId);
  }

  @Override
  public List<BankAccount> list(final String userId, final Page page) {
    return connection.queryForList(BankAccount.class, HttpMethod.GET, "/users/{0}/bankaccounts", Filter.none(), Sort.byDefault(), page, userId);
  }
}
