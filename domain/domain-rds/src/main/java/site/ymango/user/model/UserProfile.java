package site.ymango.user.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.enums.PerferMbti;

@Builder
public record UserProfile(
    Long userProfileId,
    Long userId,
    String username,
    Gender gender,
    LocalDate birthdate,
    String sido,
    String sigungu,
    Mbti mbti,
    PerferMbti preferMbti,
    Location location,
    Company userCompany,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}
