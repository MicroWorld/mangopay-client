package org.microworld.mangopay.implementation;

import org.microworld.mangopay.CardRegistrationService;
import org.microworld.mangopay.EventService;
import org.microworld.mangopay.HookService;
import org.microworld.mangopay.MangopayClient;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.PayInService;
import org.microworld.mangopay.UserService;
import org.microworld.mangopay.WalletService;

public class DefaultMangopayClient implements MangopayClient {
  private final MangopayConnection connection;

  public DefaultMangopayClient(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public CardRegistrationService getCardRegistrationService() {
    return new DefaultCardResgistrationService(connection);
  }

  @Override
  public EventService getEventService() {
    return new DefaultEventService(connection);
  }

  @Override
  public HookService getHookService() {
    return new DefaultHookService(connection);
  }

  @Override
  public PayInService getPayInService() {
    return new DefaultPayInService(connection);
  }

  @Override
  public UserService getUserService() {
    return new DefaultUserService(connection);
  }

  @Override
  public WalletService getWalletService() {
    return new DefaultWalletService(connection);
  }
}
