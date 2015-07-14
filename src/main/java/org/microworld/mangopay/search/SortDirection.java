package org.microworld.mangopay.search;

public enum SortDirection {
  ASCENDING("asc"), DESCENDING("desc");

  private final String value;

  private SortDirection(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
