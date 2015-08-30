package org.microworld.mangopay.services;

import org.microworld.mangopay.entities.BankAccount;

public interface BankAccountService {
  BankAccount create(String userId, BankAccount bankAccount);

  BankAccount get(String userId, String bankAccountId);
}
