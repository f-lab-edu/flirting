package site.ymango.point.enums;

public enum EventType {
  USER_SIGN_UP(10, "회원가입"),
  PURCHASE(null, "구매"),
  REFUND(null, "환불"),
  RECOMMEND_PROFILE(10, "프로필 추천"),
  DAILY_RECOMMEND_PROFILE(0, "일일 프로필 추천"),
  DAILY_LOGIN(2, "일일 로그인"),
  EXPIRATION(null, "포인트 만료"),
  ;

  private Integer point;
  private String summary;

  EventType(Integer point, String summary) {
    this.point = point;
    this.summary = summary;
  }

  public Integer getPoint() {
    return point;
  }

  public String getSummary() {
    return summary;
  }
}
