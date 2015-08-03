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
package org.microworld.mangopay.exceptions;

import org.microworld.mangopay.entities.Error;

public class MangopayException extends RuntimeException {
  private static final long serialVersionUID = 2731523113620863203L;

  public MangopayException(final Error error) {
    super(createMessage(error));
  }

  private static String createMessage(final Error error) {
    final StringBuilder message = new StringBuilder();
    message.append(error.getType()).append(": ").append(error.getMessage());
    error.getErrors().forEach((k, v) -> message.append("\n").append(k).append(": ").append(v));
    return message.toString();
  }
}
