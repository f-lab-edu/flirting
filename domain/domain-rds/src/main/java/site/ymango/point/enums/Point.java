package site.ymango.point.enums;

public enum Point {
  RECOMMEND_PROFILE(15, "신규 프로필 추천"),
  ;

  private final Integer point;

  private final String description;

  Point(Integer point, String description) {
    this.point = point;
    this.description = description;
  }

  public Integer getPoint() {
    return point;
  }

  public String getDescription() {
    return description;
  }
}
