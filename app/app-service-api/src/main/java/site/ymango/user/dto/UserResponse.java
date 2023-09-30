package site.ymango.user.dto;

import java.time.LocalDate;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;

public record UserResponse(
    String email,
    String username,
    Gender gender,
    LocalDate birthdate,
    String sido,
    String sigungu,
    Mbti mbti,
    String preferMbti,
    String companyName,
    String companyDomain,
    String companyIconUrl
) {

}
