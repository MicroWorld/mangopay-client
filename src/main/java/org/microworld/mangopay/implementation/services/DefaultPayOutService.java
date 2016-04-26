package org.microworld.mangopay.implementation.services;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.BankWirePayOut;
import org.microworld.mangopay.misc.HttpMethod;
import org.microworld.mangopay.services.PayOutService;

public class DefaultPayOutService implements PayOutService {
  private final MangopayConnection connection;

  public DefaultPayOutService(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public BankWirePayOut createBankWirePayOut(final BankWirePayOut bankWirePayOut) {
    return connection.queryForObject(BankWirePayOut.class, HttpMethod.POST, "/payouts/bankwire", bankWirePayOut);
  }
}
