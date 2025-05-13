package edu.esprit.payment.exeception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NotFoundException extends RuntimeException {

  private final String message;

  public NotFoundException(String message) {
    super(message);
    this.message = message;
  }
}
