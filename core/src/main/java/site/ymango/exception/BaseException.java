package site.ymango.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BaseException extends RuntimeException {
  private ErrorCode code;
  private String message;
  private String data;

  public BaseException(ErrorCode code) {
    this.code = code;
    this.message = code.getMessage();
  }

  public BaseException(ErrorCode code, String message) {
    this.code = code;
    this.message = message;
  }
}
