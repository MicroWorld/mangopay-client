package org.microworld.mangopay.implementation;

import org.microworld.mangopay.CardRegistrationApi;
import org.microworld.mangopay.EventApi;
import org.microworld.mangopay.HookApi;
import org.microworld.mangopay.MangopayClient;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.PayInApi;
import org.microworld.mangopay.UserApi;
import org.microworld.mangopay.WalletApi;

public class DefaultMangopayClient implements MangopayClient {
  private final MangopayConnection connection;

  public DefaultMangopayClient(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public CardRegistrationApi getCardRegistrationApi() {
    return new DefaultCardResgistrationApi(connection);
  }

  @Override
  public EventApi getEventApi() {
    return new DefaultEventApi(connection);
  }

  @Override
  public HookApi getHookApi() {
    return new DefaultHookApi(connection);
  }

  @Override
  public PayInApi getPayInApi() {
    return new DefaultPayInApi(connection);
  }

  @Override
  public UserApi getUserApi() {
    return new DefaultUserApi(connection);
  }

  @Override
  public WalletApi getWalletApi() {
    return new DefaultWalletApi(connection);
  }
}
