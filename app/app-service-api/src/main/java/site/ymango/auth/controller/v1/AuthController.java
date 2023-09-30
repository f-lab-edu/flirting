package site.ymango.auth.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.ymango.auth.dto.request.AuthenticationRequest;
import site.ymango.auth.dto.request.RegisterRequest;
import site.ymango.auth.dto.response.AuthenticationResponse;
import site.ymango.auth.service.AuthService;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/authenticate")
  public AuthenticationResponse authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return authService.authenticate(request);
  }

  @PostMapping("/register")
  public AuthenticationResponse register(@Valid @RequestBody RegisterRequest request) {
    return authService.register(request);
  }
}
