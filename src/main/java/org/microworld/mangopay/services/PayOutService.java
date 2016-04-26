package org.microworld.mangopay.services;

import org.microworld.mangopay.entities.BankWirePayOut;

public interface PayOutService {
  BankWirePayOut createBankWirePayOut(final BankWirePayOut createBankWirePayOut);
}
