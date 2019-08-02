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
package org.microworld.mangopay.implementation.serialization;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.YearMonth;

import org.junit.Test;

public class YearMonthAdapterTest {
  @Test
  public void toYearMonthString() {
    assertThat(YearMonthAdapter.toYearMonthString(YearMonth.of(2000, 1)), is(equalTo("0100")));
    assertThat(YearMonthAdapter.toYearMonthString(YearMonth.of(2018, 7)), is(equalTo("0718")));
    assertThat(YearMonthAdapter.toYearMonthString(YearMonth.of(2099, 12)), is(equalTo("1299")));
  }

  @Test
  public void toYearMonth() {
    assertThat(YearMonthAdapter.toYearMonth("0100"), is(equalTo(YearMonth.of(2000, 1))));
    assertThat(YearMonthAdapter.toYearMonth("0718"), is(equalTo(YearMonth.of(2018, 7))));
    assertThat(YearMonthAdapter.toYearMonth("1299"), is(equalTo(YearMonth.of(2099, 12))));
  }
}
