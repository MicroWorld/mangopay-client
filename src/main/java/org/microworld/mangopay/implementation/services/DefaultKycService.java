package org.microworld.mangopay.implementation.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.kyc.KycDocument;
import org.microworld.mangopay.entities.kyc.KycPage;
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

  @Override
  public void uploadPage(final String userId, final String documentId, final InputStream page) throws IOException {
    final String base64content = toBase64(page);
    connection.query(HttpMethod.POST, "/users/{0}/KYC/documents/{1}/pages", new KycPage(base64content), userId, documentId);
  }

  private String toBase64(final InputStream in) throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    int data;
    while ((data = in.read()) != -1) {
      out.write(data);
    }
    return Base64.getEncoder().encodeToString(out.toByteArray());
  }
}
