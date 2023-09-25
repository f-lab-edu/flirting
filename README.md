# 플러팅: 직장인 MBTI 소개팅 앱

> **플러팅**은 회사 이메일을 통해 인증하고 MBTI와 위치 기반으로 매칭되는 소개팅 앱입니다.

## 모듈 계층 구성
- `application`: 독립적으로 실행가능한 애플리케이션 모듈 계층
- `core`: 공통 모듈 (Type, Util..)
- `domain`: 시스템의 중심 도메인을 다루는 모듈
- `independent`: 플러팅 앱과 무관하게 자체로서 독립적인 역할을 갖는 모듈

참고: [멀티모듈 설계 이야기 with Spring, Gradle](https://techblog.woowahan.com/2637/)