package site.ymango.auth.dto.request;

import jakarta.validation.constraints.Email;

public record SendVerifyEmailRequest(
    @Email String email
) {

}
