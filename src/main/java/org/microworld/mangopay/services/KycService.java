package org.microworld.mangopay.services;

import java.io.IOException;
import java.io.InputStream;

import org.microworld.mangopay.entities.kyc.KycDocument;

public interface KycService {
  KycDocument createDocument(String userId, KycDocument document);

  KycDocument getDocument(String userId, String documentId);

  KycDocument validateDocument(String userId, KycDocument document);

  void uploadPage(String userId, String documentId, InputStream page) throws IOException;
}
