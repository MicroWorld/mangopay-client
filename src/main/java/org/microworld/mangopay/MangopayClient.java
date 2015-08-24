package org.microworld.mangopay;

import org.microworld.mangopay.implementation.DefaultMangopayClient;

public interface MangopayClient {
  static MangopayClient createDefault(final MangopayConnection connection) {
    return new DefaultMangopayClient(connection);
  }

  CardRegistrationService getCardRegistrationService();

  EventService getEventService();

  HookService getHookService();

  PayInService getPayInService();

  UserService getUserService();

  WalletService getWalletService();
}
