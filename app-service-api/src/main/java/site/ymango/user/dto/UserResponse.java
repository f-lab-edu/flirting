package site.ymango.user.dto;

import java.time.LocalDate;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.enums.PerferMbti;

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
