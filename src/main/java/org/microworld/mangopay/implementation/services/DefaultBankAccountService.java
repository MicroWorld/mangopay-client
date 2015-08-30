package org.microworld.mangopay.implementation.services;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.BankAccount;
import org.microworld.mangopay.misc.HttpMethod;
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
  public BankAccount get(final String userId, final String bankAccountId) {
    return connection.queryForObject(BankAccount.class, HttpMethod.GET, "/users/{0}/bankaccounts/{1}", null, userId, bankAccountId);
  }
}
