package org.microworld.mangopay.implementation.services;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.KycDocument;
import org.microworld.mangopay.misc.HttpMethod;
import org.microworld.mangopay.services.KycService;

public class DefaultKycService implements KycService {
  private final MangopayConnection connection;

  public DefaultKycService(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public KycDocument createDocument(final String userId, final KycDocument document) {
    return connection.queryForObject(KycDocument.class, HttpMethod.POST, "/users/{0}/KYC/documents", document, userId);
  }

  @Override
  public KycDocument getDocument(final String userId, final String documentId) {
    return connection.queryForObject(KycDocument.class, HttpMethod.GET, "/users/{0}/KYC/documents/{1}", null, userId, documentId);
  }

  @Override
  public KycDocument validateDocument(final String userId, final KycDocument document) {
    document.setStatusToValidationAsked();
    return connection.queryForObject(KycDocument.class, HttpMethod.PUT, "/users/{0}/KYC/documents/{1}", document, userId, document.getId());
  }
}
