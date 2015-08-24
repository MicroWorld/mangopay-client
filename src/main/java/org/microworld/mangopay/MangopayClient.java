package org.microworld.mangopay;

import org.microworld.mangopay.implementation.DefaultMangopayClient;
import org.microworld.mangopay.services.CardRegistrationService;
import org.microworld.mangopay.services.EventService;
import org.microworld.mangopay.services.HookService;
import org.microworld.mangopay.services.PayInService;
import org.microworld.mangopay.services.UserService;
import org.microworld.mangopay.services.WalletService;

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
