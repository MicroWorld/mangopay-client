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
import java.time.LocalDate;
import java.util.Currency;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.exceptions.MangopayException;

public class WalletIT extends AbstractIntegrationTest {
  @Test
  public void createGetUpdateWallet() {
    final User walletOwner = client.getUserService().create(UserIT.createNaturalUser("foo@bar.com", "Foo", "Bar", "Address", LocalDate.of(1970, 1, 1), "FR", "FR", null, null, null));

    final Wallet createdWallet = client.getWalletService().create(new Wallet(walletOwner.getId(), EUR, "Euro account", null));
    assertThat(createdWallet, is(wallet(walletOwner.getId(), EUR, "Euro account", null, Instant.now())));

    final Wallet fetchedWallet = client.getWalletService().get(createdWallet.getId());
    assertThat(fetchedWallet, is(wallet(walletOwner.getId(), EUR, "Euro account", null, Instant.now())));
    assertThat(fetchedWallet.getId(), is(equalTo(createdWallet.getId())));

    fetchedWallet.setDescription("EUR account");
    fetchedWallet.setTag("Something");
    final Wallet updatedWallet = client.getWalletService().update(fetchedWallet);
    assertThat(updatedWallet, is(wallet(walletOwner.getId(), EUR, "EUR account", "Something", Instant.now())));
    assertThat(updatedWallet.getId(), is(equalTo(fetchedWallet.getId())));
  }

  @Test
  public void getWalletWithInvalidId() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("ressource_not_found: The ressource does not exist");
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource Wallet with the id=10");
    client.getWalletService().get("10");
  }

  @Test
  public void createWalletWithMissingDescription() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Description: The Description field is required.");
    client.getWalletService().create(new Wallet("7589576", USD, null, null));
  }

  @Test
  public void createWalletWithMissingOwner() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("externalOwnerId: The value  is not valid");
    client.getWalletService().create(new Wallet(null, USD, "The description", null));
  }

  @Test
  public void createWalletWithFieldsContainingInvalidValues() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("currency_not_available: Error: the currency used is not available");
    thrown.expectMessage("currency: The currency XAF is not available or has been disabled");
    client.getWalletService().create(new Wallet("10", XAF, "Invalid wallet", null));
  }

  private Matcher<Wallet> wallet(final String ownerId, final Currency currency, final String description, final String tag, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("ownerId", is(equalTo(ownerId))),
        hasProperty("currency", is(equalTo(currency))),
        hasProperty("description", is(equalTo(description))),
        hasProperty("tag", is(equalTo(tag))),
        hasProperty("creationDate", is(around(creationDate))));
  }
}
