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
