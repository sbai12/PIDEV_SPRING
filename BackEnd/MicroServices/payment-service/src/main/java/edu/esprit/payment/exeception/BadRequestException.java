package edu.esprit.payment.exeception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BadRequestException extends RuntimeException {

  private final String message;

  public BadRequestException(String message) {
    super(message);
    this.message = message;
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }
}
