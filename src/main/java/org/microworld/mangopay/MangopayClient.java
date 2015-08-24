package org.microworld.mangopay;

import org.microworld.mangopay.implementation.DefaultMangopayClient;

public interface MangopayClient {
  static MangopayClient createDefault(final MangopayConnection connection) {
    return new DefaultMangopayClient(connection);
  }

  CardRegistrationApi getCardRegistrationApi();

  EventApi getEventApi();

  HookApi getHookApi();

  PayInApi getPayInApi();

  UserApi getUserApi();

  WalletApi getWalletApi();
}
