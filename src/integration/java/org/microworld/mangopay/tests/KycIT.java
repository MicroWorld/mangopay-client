/**
 * Copyright (C) 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.microworld.test.Matchers.around;

import java.time.Instant;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.microworld.mangopay.entities.KycDocument;
import org.microworld.mangopay.entities.KycDocumentStatus;
import org.microworld.mangopay.entities.KycDocumentType;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.exceptions.MangopayException;

public class KycIT extends AbstractIntegrationTest {
  private User user;

  @Before
  public void setUpUser() {
    user = client.getUserService().create(randomNaturalUser());
  }

  @Test
  public void createGetAndValidateDocument() {
    final Instant creationDate = Instant.now();

    final KycDocument kycDocument = new KycDocument(KycDocumentType.IDENTITY_PROOF, fairy.textProducer().latinSentence());
    final KycDocument createdKycDocument = client.getKycService().createDocument(user.getId(), kycDocument);
    assertThat(createdKycDocument, is(kycDocument(kycDocument, KycDocumentStatus.CREATED, creationDate)));

    final KycDocument fetchedKycDocument = client.getKycService().getDocument(user.getId(), createdKycDocument.getId());
    assertThat(fetchedKycDocument, is(kycDocument(createdKycDocument, KycDocumentStatus.CREATED, creationDate)));
    assertThat(fetchedKycDocument.getId(), is(equalTo(createdKycDocument.getId())));

    final KycDocument askedValidationKycDocument = client.getKycService().validateDocument(user.getId(), fetchedKycDocument);
    assertThat(askedValidationKycDocument, is(kycDocument(fetchedKycDocument, KycDocumentStatus.VALIDATION_ASKED, creationDate)));
    assertThat(askedValidationKycDocument.getId(), is(equalTo(fetchedKycDocument.getId())));
  }

  @Test
  public void createDocumentWithMissingMandatoryFields() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Type: The Type field is required.");
    client.getKycService().createDocument(user.getId(), new KycDocument(null, null));
  }

  @Test
  public void getDocumentWithInvalidId() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("ressource_not_found: The ressource does not exist");
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource Document with the id=10");
    client.getKycService().getDocument(user.getId(), "10");
  }

  private Matcher<KycDocument> kycDocument(final KycDocument kycDocument, final KycDocumentStatus status, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("type", is(equalTo(kycDocument.getType()))),
        hasProperty("status", is(equalTo(status))),
        hasProperty("creationDate", is(around(creationDate))),
        hasProperty("tag", is(equalTo(kycDocument.getTag()))));
  }
}
