package site.ymango.advice;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.ymango.dto.BaseResponse;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;

@RestControllerAdvice
public class BaseExceptionAdvice extends ResponseEntityExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception e) {
    return handleException(e, e.getLocalizedMessage(), ErrorCode.UNKNOWN_ERROR, null);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<Object> handleServletException(BaseException e) {
    return handleBaseException(e);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return new ResponseEntity<>(BaseResponse.builder()
        .code(ErrorCode.INVALID_INPUT_VALUE.getCode())
        .message(ErrorCode.INVALID_INPUT_VALUE.getMessage())
        .errors(ex.getBindingResult().getFieldErrors())
        .build(), status);
  }

  private ResponseEntity<Object> handleBaseException(BaseException e) {
    return handleException(e, e.getMessage(), e.getCode(), e.getData());
  }

  private <T> ResponseEntity<T> handleException(Exception e, String message, ErrorCode errorCode, T data) {
    String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
    BaseResponse exceptionResponse = BaseResponse.builder()
        .code(errorCode.getCode())
        .message(message)
        .data(data)
        .detail(rootCauseMessage)
        .build();
    return new ResponseEntity<T>((T) exceptionResponse, errorCode.getStatus());
  }
}
