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

import org.junit.Test;

import java.time.LocalDate;
import java.util.TimeZone;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class LocalDateAdapterTest {
    @Test
    public void toTimestamp() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-1:00"));
        assertThat(LocalDateAdapter.toTimestamp(LocalDate.of(1970, 1, 1)), is(equalTo(0L)));
        assertThat(LocalDateAdapter.toTimestamp(LocalDate.of(2015, 7, 13)), is(equalTo(1436745600L)));

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        assertThat(LocalDateAdapter.toTimestamp(LocalDate.of(1970, 1, 1)), is(equalTo(0L)));
        assertThat(LocalDateAdapter.toTimestamp(LocalDate.of(2015, 7, 13)), is(equalTo(1436745600L)));

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        assertThat(LocalDateAdapter.toTimestamp(LocalDate.of(1970, 1, 1)), is(equalTo(0L)));
        assertThat(LocalDateAdapter.toTimestamp(LocalDate.of(2015, 7, 13)), is(equalTo(1436745600L)));
    }

    @Test
    public void toLocalDateTime() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-1:00"));
        assertThat(LocalDateAdapter.toLocalDate(0).format(ISO_DATE), is(equalTo("1970-01-01")));
        assertThat(LocalDateAdapter.toLocalDate(1436746722).format(ISO_DATE), is(equalTo("2015-07-13")));

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        assertThat(LocalDateAdapter.toLocalDate(0).format(ISO_DATE), is(equalTo("1970-01-01")));
        assertThat(LocalDateAdapter.toLocalDate(1436746722).format(ISO_DATE), is(equalTo("2015-07-13")));

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        assertThat(LocalDateAdapter.toLocalDate(0).format(ISO_DATE), is(equalTo("1970-01-01")));
        assertThat(LocalDateAdapter.toLocalDate(1436746722).format(ISO_DATE), is(equalTo("2015-07-13")));
    }
}
