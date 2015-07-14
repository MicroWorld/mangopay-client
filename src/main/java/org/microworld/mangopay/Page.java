package org.microworld.mangopay;

import java.util.HashMap;
import java.util.Map;

public class Page {
  public static final int DEFAULT_PAGE_SIZE = 10;
  public static final int MAX_PAGE_SIZE = 100;
  private final Map<String, String> parameters = new HashMap<>();

  private Page(final int pageNumber, final int pageSize) {
    parameters.put("page", String.valueOf(pageNumber));
    parameters.put("per_page", String.valueOf(pageSize));
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public static Page of(final int pageNumber) {
    return of(pageNumber, DEFAULT_PAGE_SIZE);
  }

  public static Page of(final int pageNumber, final int pageSize) {
    return new Page(pageNumber, pageSize);
  }
}
