![Flirting](https://capsule-render.vercel.app/api?type=wave&color=0:8971D0,100:7DACE4&height=300&section=header&text=Flirting&fontSize=90&fontColor=FFFF)

## 플러팅: 직장인 MBTI 소개팅 앱

> **플러팅**은 회사 이메일을 통해 인증하고 MBTI와 위치 기반으로 매칭되는 소개팅 앱입니다.

## 기술 스택
![java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![spring](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![spring security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![spring batch](https://img.shields.io/badge/SpringBatch-6DB33F?style=for-the-badge&logo=mysql&logoColor=white)
![kafka](https://img.shields.io/badge/kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white)
![mysql](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-00000F?style=for-the-badge&logo=jpa&logoColor=white)
![jpa](https://img.shields.io/badge/JPA-00000F?style=for-the-badge&logo=jpa&logoColor=white)
![navercloud](https://img.shields.io/badge/NaverCloudPlatform-03C75A?style=for-the-badge&logo=naver&logoColor=white)



## 서버 아키텍처
![서버 아키텍처](./img.png)

## 모듈 계층 구성
플러팅 api는 멀티 모듈 프로젝트로 구성되어 있습니다.

```mermaid
flowchart TB
    subgraph 애플리케이션
		app_service_api[app-service-api]
		app_event_consumer[app-event-consumer]
    end

    subgraph 내부모듈
		gateway
		notification
		end

    subgraph 도메인
		domain
		end

    subgraph 공통
		core
		end

    애플리케이션 --> 도메인
		애플리케이션 --> 공통
		애플리케이션 --> 내부모듈
		도메인 --> 공통
		내부모듈 --> 공통
```

참고: [멀티모듈 설계 이야기 with Spring, Gradle](https://techblog.woowahan.com/2637/)

### 애플리케이션
> 독립적으로 실행가능한 애플리케이션 모듈 계층
- `app-service-api`: 플러팅 앱 서비스 API (**port: 8880**)
- `app-point-consumer`: 포인트 이벤트 컨슈머 (**port: 8881**)

### 공통
> 공통 모듈 (Type, Util..)
- `core`: 공통 모듈

### 도메인
> 시스템의 중심 도메인을 다루는 모듈
- `domain-rds`: 관계형 데이터베이스 도메인
- `domain-redis`: Redis 도메인

### 내부모듈
> 시스템 전체적인 기능을 서포트하기 위한 기능 모듈
- `gateway`: API Gateway
- `notification`: 알림 모듈

## API 명세서
- [API 명세서](https://documenter.getpostman.com/view/9820015/2s9YJXYPqg)

## ERD
![ERD](https://user-images.githubusercontent.com/30119526/275329767-596acaf4-8dda-4a1e-8aec-221a87090641.png)