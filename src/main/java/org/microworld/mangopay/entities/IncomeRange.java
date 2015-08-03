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
package org.microworld.mangopay.entities;

public enum IncomeRange {
  BELOW_18k€("1"),
  BETWEEN_18_AND_30k€("2"),
  BETWEEN_30_AND_50k€("3"),
  BETWEEN_50_AND_80k€("4"),
  BETWEEN_80_AND_120k€("5"),
  ABOVE_120k€("6");

  private final String value;

  private IncomeRange(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static IncomeRange parse(final String value) {
    for (final IncomeRange incomeRange : values()) {
      if (incomeRange.getValue().equalsIgnoreCase(value)) {
        return incomeRange;
      }
    }
    throw new IllegalArgumentException(value);
  }
}
