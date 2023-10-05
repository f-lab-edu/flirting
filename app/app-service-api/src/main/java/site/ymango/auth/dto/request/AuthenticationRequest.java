package site.ymango.auth.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record AuthenticationRequest(
    @Email
    String email,
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*\\\\W).{8,20}$")
    String password
) {

}
