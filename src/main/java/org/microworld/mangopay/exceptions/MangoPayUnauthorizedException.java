package org.microworld.mangopay.exceptions;

public class MangoPayUnauthorizedException extends RuntimeException {
  private static final long serialVersionUID = -7283031306676559431L;

  public MangoPayUnauthorizedException(final String error, final String errorDescription) {
    super(error + ": " + errorDescription);
  }
}
