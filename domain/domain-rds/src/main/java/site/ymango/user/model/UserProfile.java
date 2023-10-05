package site.ymango.user.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;

@Builder
public record UserProfile(
    Long userProfileId,
    String username,
    Gender gender,
    LocalDate birthdate,
    String sido,
    String sigungu,
    Mbti mbti,
    String preferMbti,
    Location location,
    UserCompany userCompany,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}
