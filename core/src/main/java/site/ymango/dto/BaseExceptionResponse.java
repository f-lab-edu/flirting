package site.ymango.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.FieldError;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseExceptionResponse<T> extends BaseResponse<T> {
  private String detail;
  private Map<String, FieldError> errors;
  private StackTraceElement[] trace;
}
