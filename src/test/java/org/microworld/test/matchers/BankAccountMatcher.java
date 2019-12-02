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
package org.microworld.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;
import org.microworld.mangopay.entities.bankaccounts.BritishBankAccount;
import org.microworld.mangopay.entities.bankaccounts.CanadianBankAccount;
import org.microworld.mangopay.entities.bankaccounts.IbanBankAccount;
import org.microworld.mangopay.entities.bankaccounts.OtherBankAccount;
import org.microworld.mangopay.entities.bankaccounts.UsaBankAccount;

import java.time.Instant;

public class BankAccountMatcher extends TypeSafeDiagnosingMatcher<BankAccount> {
    private final BankAccount expected;
    private boolean nonNullId;
    private Instant creationDate;

    public BankAccountMatcher(final BankAccount bankAccount) {
        this.expected = bankAccount;
    }

    public BankAccountMatcher(final BankAccount bankAccount, final boolean nonNullId, final Instant creationDate) {
        this.expected = bankAccount;
        this.nonNullId = nonNullId;
        this.creationDate = creationDate;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendValue(expected);
    }

    @Override
    protected boolean matchesSafely(final BankAccount actual, final Description mismatchDescription) {
        if (expected.getClass().isInstance(actual) && isEqualTo(actual)) {
            return true;
        }
        mismatchDescription.appendValue(actual);
        return false;
    }

    private boolean isEqualTo(final BankAccount actual) {
        if (nonNullId && actual.getId() == null) {
            return false;
        }
        if (!nonNullId && !IsEqual.equalTo(expected.getId()).matches(actual.getId())) {
            return false;
        }
        if (creationDate == null && !IsEqual.equalTo(expected.getCreationDate()).matches(actual.getCreationDate())) {
            return false;
        }
        if (creationDate != null && !IsAround.around(creationDate).matches(actual.getCreationDate())) {
            return false;
        }
        if (!IsEqual.equalTo(expected.getTag()).matches(actual.getTag())) {
            return false;
        }
        if (!IsEqual.equalTo(expected.getType()).matches(actual.getType())) {
            return false;
        }
        if (!IsEqual.equalTo(expected.getOwnerName()).matches(actual.getOwnerName())) {
            return false;
        }
        if (!IsEqual.equalTo(expected.getOwnerAddress()).matches(actual.getOwnerAddress())) {
            return false;
        }
        if (actual instanceof IbanBankAccount && expected instanceof IbanBankAccount) {
            if (!IsEqual.equalTo(((IbanBankAccount) expected).getIban()).matches(((IbanBankAccount) actual).getIban())) {
                return false;
            }
            return IsEqual.equalTo(((IbanBankAccount) expected).getBic()).matches(((IbanBankAccount) actual).getBic());
        } else if (actual instanceof BritishBankAccount && expected instanceof BritishBankAccount) {
            if (!IsEqual.equalTo(((BritishBankAccount) expected).getAccountNumber()).matches(((BritishBankAccount) actual).getAccountNumber())) {
                return false;
            }
            return IsEqual.equalTo(((BritishBankAccount) expected).getSortCode()).matches(((BritishBankAccount) actual).getSortCode());
        } else if (actual instanceof CanadianBankAccount && expected instanceof CanadianBankAccount) {
            if (!IsEqual.equalTo(((CanadianBankAccount) expected).getAccountNumber()).matches(((CanadianBankAccount) actual).getAccountNumber())) {
                return false;
            }
            if (!IsEqual.equalTo(((CanadianBankAccount) expected).getBankName()).matches(((CanadianBankAccount) actual).getBankName())) {
                return false;
            }
            if (!IsEqual.equalTo(((CanadianBankAccount) expected).getBranchCode()).matches(((CanadianBankAccount) actual).getBranchCode())) {
                return false;
            }
            return IsEqual.equalTo(((CanadianBankAccount) expected).getInstitutionNumber()).matches(((CanadianBankAccount) actual).getInstitutionNumber());
        } else if (actual instanceof UsaBankAccount && expected instanceof UsaBankAccount) {
            if (!IsEqual.equalTo(((UsaBankAccount) expected).getAccountNumber()).matches(((UsaBankAccount) actual).getAccountNumber())) {
                return false;
            }
            if (!IsEqual.equalTo(((UsaBankAccount) expected).getAba()).matches(((UsaBankAccount) actual).getAba())) {
                return false;
            }
            return IsEqual.equalTo(((UsaBankAccount) expected).getDepositAccountType()).matches(((UsaBankAccount) actual).getDepositAccountType());
        } else if (actual instanceof OtherBankAccount && expected instanceof OtherBankAccount) {
            if (!IsEqual.equalTo(((OtherBankAccount) expected).getAccountNumber()).matches(((OtherBankAccount) actual).getAccountNumber())) {
                return false;
            }
            if (!IsEqual.equalTo(((OtherBankAccount) expected).getBic()).matches(((OtherBankAccount) actual).getBic())) {
                return false;
            }
            return IsEqual.equalTo(((OtherBankAccount) expected).getCountry()).matches(((OtherBankAccount) actual).getCountry());
        }
        return true;
    }
}
