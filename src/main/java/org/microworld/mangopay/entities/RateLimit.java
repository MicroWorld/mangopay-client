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
package org.microworld.mangopay.entities;

import java.time.Instant;

public class RateLimit {
  private final int callsMade;
  private final int callsRemaining;
  private final Instant reset;

  public RateLimit(final int callsMade, final int callsRemaining, final Instant reset) {
    this.callsMade = callsMade;
    this.callsRemaining = callsRemaining;
    this.reset = reset;
  }

  public int getCallsMade() {
    return callsMade;
  }

  public int getCallsRemaining() {
    return callsRemaining;
  }

  public Instant getReset() {
    return reset;
  }
}
