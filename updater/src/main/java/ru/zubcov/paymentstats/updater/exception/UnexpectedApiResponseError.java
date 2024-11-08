package ru.zubcov.paymentstats.updater.exception;

public class UnexpectedApiResponseError extends RuntimeException {
  public UnexpectedApiResponseError(String message) {
    super(message);
  }
}
