package site.ymango.auth.dto.request;


public record AuthenticationRequest(
    String email,
    String password
) {


}
