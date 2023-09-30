package site.ymango.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;

public record RegisterRequest(
    String email,
    String password,
    Gender gender,
    String username,
    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
    Mbti mbti,
    String preferMbti,
    String sido,
    String sigungu,
    Double latitude,
    Double longitude
) {

}
