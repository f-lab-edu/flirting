package site.ymango.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.enums.PerferMbti;

public record RegisterRequest(
    @Email
    String email,
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}$")
    String password,
    Gender gender,
    @Length(min = 2, max = 10)
    String username,
    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
    Mbti mbti,
    PerferMbti preferMbti,
    @Length(min = 2, max = 10)
    String sido,
    @Length(min = 2, max = 10)
    String sigungu,
    Double latitude,
    Double longitude
) {

}
