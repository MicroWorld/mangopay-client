package org.microworld.mangopay.exceptions;

import org.microworld.mangopay.entities.Error;

public class MangoPayException extends RuntimeException {
  private static final long serialVersionUID = 2731523113620863203L;

  public MangoPayException(final Error error) {
    super(createMessage(error));
  }

  private static String createMessage(final Error error) {
    final StringBuilder message = new StringBuilder();
    message.append(error.getType()).append(": ").append(error.getMessage());
    error.getErrors().forEach((k, v) -> message.append("\n").append(k).append(": ").append(v));
    return message.toString();
  }
}
