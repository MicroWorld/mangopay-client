package org.microworld.mangopay;

import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.implementation.DefaultWalletApi;

public interface WalletApi {
  static WalletApi createDefault(final MangoPayConnection connection) {
    return new DefaultWalletApi(connection);
  }

  Wallet create(final Wallet wallet);

  Wallet get(String id);

  Wallet update(Wallet wallet);
}
