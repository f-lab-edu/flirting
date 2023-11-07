package site.ymango.user.dto;

import java.time.LocalDate;
import lombok.Builder;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.enums.PerferMbti;

@Builder
public record UserResponse(
    String email,
    String username,
    Gender gender,
    LocalDate birthdate,
    String sido,
    String sigungu,
    Mbti mbti,
    PerferMbti preferMbti,
    String companyName,
    String companyDomain,
    String companyIconUrl
) {

}
