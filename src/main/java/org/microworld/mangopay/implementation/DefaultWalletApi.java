package org.microworld.mangopay.implementation;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.WalletApi;
import org.microworld.mangopay.entities.Wallet;

public class DefaultWalletApi implements WalletApi {
  private final MangopayConnection connection;

  public DefaultWalletApi(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public Wallet create(final Wallet wallet) {
    return connection.queryForObject(Wallet.class, HttpMethod.POST, "/wallets", wallet);
  }

  @Override
  public Wallet get(final String id) {
    return connection.queryForObject(Wallet.class, HttpMethod.GET, "/wallets/{0}", null, id);
  }

  @Override
  public Wallet update(final Wallet wallet) {
    return connection.queryForObject(Wallet.class, HttpMethod.PUT, "/wallets/{0}", wallet, wallet.getId());
  }
}
