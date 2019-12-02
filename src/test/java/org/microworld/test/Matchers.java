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
package org.microworld.test;

import org.hamcrest.Matcher;
import org.microworld.mangopay.entities.RateLimit;
import org.microworld.mangopay.entities.RateLimitInterval;
import org.microworld.mangopay.entities.Token;
import org.microworld.mangopay.entities.bankaccounts.BankAccount;
import org.microworld.test.matchers.BankAccountMatcher;
import org.microworld.test.matchers.IsAfter;
import org.microworld.test.matchers.IsAround;
import org.microworld.test.matchers.IsBefore;
import org.microworld.test.matchers.RateLimitMatcher;
import org.microworld.test.matchers.TokenMatcher;

import java.time.Instant;

public class Matchers {
    public static Matcher<Instant> after(final Instant instant) {
        return new IsAfter(instant);
    }

    public static Matcher<Instant> around(final Instant instant) {
        return new IsAround(instant);
    }

    public static Matcher<Instant> before(final Instant instant) {
        return new IsBefore(instant);
    }

    public static Matcher<? super BankAccount> bankAccount(final BankAccount bankAccount) {
        return new BankAccountMatcher(bankAccount);
    }

    public static Matcher<? super BankAccount> bankAccount(final BankAccount bankAccount, final boolean nonNullId, final Instant creationDate) {
        return new BankAccountMatcher(bankAccount, nonNullId, creationDate);
    }

    public static Matcher<? super RateLimit> rateLimit(final RateLimitInterval interval, final int callsMade, final int callsRemaining, final Instant reset) {
        return new RateLimitMatcher(new RateLimit(interval, callsMade, callsRemaining, reset));
    }

    public static Matcher<Token> token(final String value, final String type, final int duration) {
        return new TokenMatcher(duration, type, value);
    }
}
