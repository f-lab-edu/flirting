package site.ymango.auth.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record AuthenticationRequest(
    @Email
    String email,
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}$")
    String password
) {

}
