package org.microworld.mangopay;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.microworld.mangopay.entities.EventType;

public class Filter {
  private final Map<String, String> parameters = new HashMap<>();

  private Filter(final Map<String, String> parameters) {
    this.parameters.putAll(parameters);
  }

  private Filter(final String name, final String value) {
    parameters.put(name, value);
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public Filter and(final Filter filter) {
    final Map<String, String> combinedParameters = new HashMap<>(parameters);
    combinedParameters.putAll(filter.parameters);
    return new Filter(combinedParameters);
  }

  public static Filter none() {
    return new Filter(Collections.emptyMap());
  }

  public static Filter beforeDate(final Instant instant) {
    return new Filter("BeforeDate", String.valueOf(instant.getEpochSecond()));
  }

  public static Filter afterDate(final Instant instant) {
    return new Filter("AfterDate", String.valueOf(instant.getEpochSecond()));
  }

  public static Filter eventType(final EventType eventType) {
    return new Filter("EventType", eventType.name());
  }
}
