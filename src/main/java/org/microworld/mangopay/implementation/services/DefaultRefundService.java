package org.microworld.mangopay.implementation.services;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.Refund;
import org.microworld.mangopay.misc.HttpMethod;
import org.microworld.mangopay.services.RefundService;

public class DefaultRefundService implements RefundService {
  private final MangopayConnection connection;

  public DefaultRefundService(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public Refund get(final String id) {
    return connection.queryForObject(Refund.class, HttpMethod.GET, "/refunds/{0}", null, id);
  }
}
