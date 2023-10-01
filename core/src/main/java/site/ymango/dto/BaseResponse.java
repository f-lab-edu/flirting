package site.ymango.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
  int code;

  @Builder.Default
  String message = "success";

  T data;

  public static <T> BaseResponse<T> success(T data) {
    return BaseResponse.<T>builder().data(data).build();
  }
}