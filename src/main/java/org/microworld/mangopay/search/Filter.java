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
package org.microworld.mangopay.search;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.microworld.mangopay.entities.EventType;

public class Filter implements ParameterHolder {
  private final Map<String, String> parameters = new HashMap<>();

  private Filter(final Map<String, String> parameters) {
    this.parameters.putAll(parameters);
  }

  private Filter(final String name, final String value) {
    parameters.put(name, value);
  }

  @Override
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
