package org.microworld.mangopay.search;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Sort implements ParameterHolder {
  private final Map<String, String> parameters = new HashMap<>();

  private Sort(final Map<String, String> parameters) {
    this.parameters.putAll(parameters);
  }

  private Sort(final SortField field, final SortDirection direction) {
    parameters.put("Sort", field.getValue() + ":" + direction.getValue());
  }

  @Override
  public Map<String, String> getParameters() {
    return parameters;
  }

  public static Sort byDefault() {
    return new Sort(Collections.emptyMap());
  }

  public static Sort by(final SortField field, final SortDirection direction) {
    return new Sort(field, direction);
  }
}
