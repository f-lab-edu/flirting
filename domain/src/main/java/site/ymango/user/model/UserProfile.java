package site.ymango.user.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.MBTI;

public record UserProfile(
    Long userProfileId,
    String username,
    Gender gender,
    LocalDate birthdate,
    String sido,
    String sigungu,
    MBTI mbti,
    String preferMbti,
    Location location,
    UserCompany userCompany,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}
