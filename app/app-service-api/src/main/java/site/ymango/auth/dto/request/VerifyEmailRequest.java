package site.ymango.auth.dto.request;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record VerifyEmailRequest(
    @Email String email,
    @Length(min = 4, max = 4) String verificationNumber
) {

}
