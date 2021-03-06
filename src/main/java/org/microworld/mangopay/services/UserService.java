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
package org.microworld.mangopay.services;

import org.microworld.mangopay.entities.Card;
import org.microworld.mangopay.entities.LegalUser;
import org.microworld.mangopay.entities.NaturalUser;
import org.microworld.mangopay.entities.Transaction;
import org.microworld.mangopay.entities.User;
import org.microworld.mangopay.entities.Wallet;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;
import org.microworld.mangopay.entities.kyc.KycDocument;
import org.microworld.mangopay.search.Filter;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;

import java.util.List;

public interface UserService {
    default User create(final User user) {
        if (user instanceof LegalUser) {
            return create((LegalUser) user);
        } else if (user instanceof NaturalUser) {
            return create((NaturalUser) user);
        } else {
            throw new IllegalStateException("Only LegalUser and NaturalUser instances are allowed.");
        }
    }

    default User update(final User user) {
        if (user instanceof LegalUser) {
            return update((LegalUser) user);
        } else if (user instanceof NaturalUser) {
            return update((NaturalUser) user);
        } else {
            throw new IllegalStateException("Only LegalUser and NaturalUser instances are allowed.");
        }
    }

    LegalUser create(final LegalUser user);

    NaturalUser create(final NaturalUser user);

    User get(String id);

    LegalUser getLegalUser(String id);

    NaturalUser getNaturalUser(String id);

    LegalUser update(LegalUser user);

    NaturalUser update(NaturalUser user);

    List<User> list(Sort sort, Page page);

    List<BankAccount> getBankAccounts(String id, Sort by, Page of);

    List<Card> getCards(String userId, Sort sort, Page page);

    List<KycDocument> getKycDocuments(String userId, Filter filter, Sort sort, Page page);

    List<Wallet> getWallets(String userId, Sort sort, Page page);

    List<Transaction> getTransactions(String userId, Filter filter, Sort sort, Page page);
}
