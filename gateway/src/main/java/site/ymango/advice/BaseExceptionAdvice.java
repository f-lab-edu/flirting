package site.ymango.advice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.ymango.dto.BaseExceptionResponse;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;

@RestControllerAdvice
@RequiredArgsConstructor
public class BaseExceptionAdvice extends ResponseEntityExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseExceptionResponse<Object>> handleException(Exception e) {

    return handleException(e, e.getLocalizedMessage(), ErrorCode.UNKNOWN_ERROR);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<BaseExceptionResponse<Object>> handleServletException(BaseException e) {
    return handleBaseException(e);
  }

  private ResponseEntity<BaseExceptionResponse<Object>> handleBaseException(BaseException e) {
    return handleException(e, e.getMessage(), e.getCode());
  }

  private ResponseEntity<BaseExceptionResponse<Object>> handleException(Exception e, String message, ErrorCode errorCode) {
    String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
    BaseExceptionResponse<Object> exceptionResponse = BaseExceptionResponse.builder()
        .code(errorCode.getCode())
        .message(message)
        .detail(rootCauseMessage)
        .trace(e.getStackTrace())
        .build();
    return new ResponseEntity(exceptionResponse, errorCode.getStatus());
  }
}
