package org.microworld.mangopay.services;

import org.microworld.mangopay.entities.KycDocument;

public interface KycService {
  KycDocument createDocument(String userId, KycDocument document);

  KycDocument getDocument(String userId, String documentId);

  KycDocument validateDocument(String userId, KycDocument document);
}
