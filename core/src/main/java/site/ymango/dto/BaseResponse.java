package site.ymango.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
  private int code;

  @Builder.Default
  private String message = "success";

  private Object data;

  private String detail;
  private List<FieldError> errors;

  public static BaseResponse success(Object data) {
    return BaseResponse.builder()
        .code(200)
        .message("success")
        .data(data)
        .build();
  }
}