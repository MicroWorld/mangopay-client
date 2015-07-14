package org.microworld.mangopay.search;

public enum SortField {
  CREATION_DATE("CreationDate"),
  EXECUTION_DATE("ExecutionDate"),
  EVENT_DATE("Date");

  private final String value;

  private SortField(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
