package site.ymango.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  // auth
  AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, 1000, "인증에 실패했습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1001, "유효하지 않은 토큰입니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 1002, "만료된 토큰입니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, 1003, "접근이 거부되었습니다."),
  VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, 1004, "인증에 실패했습니다."),


  // user
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, 2000, "사용자를 찾을 수 없습니다."),
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT, 2001, "이미 존재하는 사용자입니다."),
  USER_NOT_MATCH(HttpStatus.UNAUTHORIZED, 2002, "사용자 정보가 일치하지 않습니다."),
  USER_NOT_ACTIVE(HttpStatus.UNAUTHORIZED, 2003, "사용자가 비활성화 되어 있습니다."),
  USER_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, 2004, "사용자 권한이 없습니다."),
  USER_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, 2005, "사용자가 인증되지 않았습니다."),
  USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, 2006, "이미 존재하는 닉네임 입니다."),
  USER_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, 2007, "사용자 프로필을 찾을 수 없습니다."),
  COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, 2010, "회사를 찾을 수 없습니다."),
  COMPANY_ALREADY_EXISTS(HttpStatus.CONFLICT, 2011, "이미 존재하는 회사입니다."),
  EMAIL_VERIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, 2020, "이메일 인증 정보가 유효하지 않습니다."),
  EMAIL_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, 2021, "이메일이 인증되지 않았습니다."),

  // validation
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 4000, "입력값이 올바르지 않습니다."),

  // point
  POINT_WALLET_NOT_FOUND(HttpStatus.NOT_FOUND, 5000, "포인트 지갑을 찾을 수 없습니다."),
  POINT_WALLET_ALREADY_EXISTS(HttpStatus.CONFLICT, 5001, "이미 존재하는 포인트 지갑입니다."),
  POINT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, 5002, "포인트가 부족합니다."),
  POINT_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, 5003, "포인트 내역을 찾을 수 없습니다."),

  // recommend
  RECOMMEND_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, 6000, "추천 프로필을 찾을 수 없습니다."),


  UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR ,9999, "알 수 없는 에러가 발생했습니다.");
  private final HttpStatus status;
  private final int code;
  private final String message;

  ErrorCode(HttpStatus status, int code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public static ErrorCode valueOf(int code) {
    for (ErrorCode errorCode : ErrorCode.values()) {
      if (errorCode.getCode() == code) {
        return errorCode;
      }
    }
    return UNKNOWN_ERROR;
  }
}
