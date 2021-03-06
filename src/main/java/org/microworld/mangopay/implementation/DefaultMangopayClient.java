/**
 * Copyright © 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.implementation;

import org.microworld.mangopay.MangopayClient;
import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.implementation.services.DefaultBankAccountService;
import org.microworld.mangopay.implementation.services.DefaultCardRegistrationService;
import org.microworld.mangopay.implementation.services.DefaultEventService;
import org.microworld.mangopay.implementation.services.DefaultHookService;
import org.microworld.mangopay.implementation.services.DefaultKycService;
import org.microworld.mangopay.implementation.services.DefaultPayInService;
import org.microworld.mangopay.implementation.services.DefaultPayOutService;
import org.microworld.mangopay.implementation.services.DefaultRefundService;
import org.microworld.mangopay.implementation.services.DefaultTransferService;
import org.microworld.mangopay.implementation.services.DefaultUserService;
import org.microworld.mangopay.implementation.services.DefaultWalletService;
import org.microworld.mangopay.services.BankAccountService;
import org.microworld.mangopay.services.CardRegistrationService;
import org.microworld.mangopay.services.EventService;
import org.microworld.mangopay.services.HookService;
import org.microworld.mangopay.services.KycService;
import org.microworld.mangopay.services.PayInService;
import org.microworld.mangopay.services.PayOutService;
import org.microworld.mangopay.services.RefundService;
import org.microworld.mangopay.services.TransferService;
import org.microworld.mangopay.services.UserService;
import org.microworld.mangopay.services.WalletService;

public class DefaultMangopayClient implements MangopayClient {
    private final MangopayConnection connection;

    public DefaultMangopayClient(final MangopayConnection connection) {
        this.connection = connection;
    }

    @Override
    public MangopayConnection getConnection() {
        return connection;
    }

    @Override
    public BankAccountService getBankAccountService() {
        return new DefaultBankAccountService(connection);
    }

    @Override
    public CardRegistrationService getCardRegistrationService() {
        return new DefaultCardRegistrationService(connection);
    }

    @Override
    public EventService getEventService() {
        return new DefaultEventService(connection);
    }

    @Override
    public HookService getHookService() {
        return new DefaultHookService(connection);
    }

    @Override
    public KycService getKycService() {
        return new DefaultKycService(connection);
    }

    @Override
    public PayInService getPayInService() {
        return new DefaultPayInService(connection);
    }

    @Override
    public PayOutService getPayOutService() {
        return new DefaultPayOutService(connection);
    }

    @Override
    public RefundService getRefundService() {
        return new DefaultRefundService(connection);
    }

    @Override
    public TransferService getTransferService() {
        return new DefaultTransferService(connection);
    }

    @Override
    public UserService getUserService() {
        return new DefaultUserService(connection);
    }

    @Override
    public WalletService getWalletService() {
        return new DefaultWalletService(connection);
    }
}
