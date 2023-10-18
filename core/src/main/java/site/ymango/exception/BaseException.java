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
  private Object data;
  private StackTraceElement[] trace;

  public BaseException(ErrorCode code) {
    this.code = code;
    this.message = code.getMessage();
  }

  public BaseException(ErrorCode code, Exception e) {
    this.code = code;
    this.message = e.getLocalizedMessage();
    this.trace = e.getStackTrace();
  }

  public BaseException(ErrorCode code, String message) {
    this.code = code;
    this.message = message;
  }

  public BaseException(ErrorCode code, String message, Object data) {
    this.code = code;
    this.message = message;
  }
}
