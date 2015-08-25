package org.microworld.mangopay.implementation;

import org.microworld.mangopay.MangopayClient;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.implementation.services.DefaultCardResgistrationService;
import org.microworld.mangopay.implementation.services.DefaultEventService;
import org.microworld.mangopay.implementation.services.DefaultHookService;
import org.microworld.mangopay.implementation.services.DefaultPayInService;
import org.microworld.mangopay.implementation.services.DefaultTransferService;
import org.microworld.mangopay.implementation.services.DefaultUserService;
import org.microworld.mangopay.implementation.services.DefaultWalletService;
import org.microworld.mangopay.services.CardRegistrationService;
import org.microworld.mangopay.services.EventService;
import org.microworld.mangopay.services.HookService;
import org.microworld.mangopay.services.PayInService;
import org.microworld.mangopay.services.TransferService;
import org.microworld.mangopay.services.UserService;
import org.microworld.mangopay.services.WalletService;

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
  public TransferService getTransferService() {
    return new DefaultTransferService(connection);
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
