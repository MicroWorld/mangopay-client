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
package org.microworld.mangopay;

import org.microworld.mangopay.implementation.DefaultMangopayClient;
import org.microworld.mangopay.services.BankAccountService;
import org.microworld.mangopay.services.CardRegistrationService;
import org.microworld.mangopay.services.EventService;
import org.microworld.mangopay.services.HookService;
import org.microworld.mangopay.services.PayInService;
import org.microworld.mangopay.services.TransferService;
import org.microworld.mangopay.services.UserService;
import org.microworld.mangopay.services.WalletService;

public interface MangopayClient {
  static MangopayClient createDefault(final MangopayConnection connection) {
    return new DefaultMangopayClient(connection);
  }

  BankAccountService getBankAccountService();

  CardRegistrationService getCardRegistrationService();

  EventService getEventService();

  HookService getHookService();

  PayInService getPayInService();

  TransferService getTransferService();

  UserService getUserService();

  WalletService getWalletService();
}
