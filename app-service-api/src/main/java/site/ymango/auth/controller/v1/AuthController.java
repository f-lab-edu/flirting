package site.ymango.auth.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.ymango.auth.dto.request.AuthenticationRequest;
import site.ymango.auth.dto.request.RegisterRequest;
import site.ymango.auth.dto.request.SendVerifyEmailRequest;
import site.ymango.auth.dto.request.VerifyEmailRequest;
import site.ymango.auth.dto.response.AuthenticationResponse;
import site.ymango.auth.service.AuthService;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/authenticate")
  public AuthenticationResponse authenticate(
      @Valid @RequestBody AuthenticationRequest request
  ) {
    return authService.authenticate(request);
  }

  @PostMapping("/register")
  public AuthenticationResponse register(
      @RequestHeader("X-FLIRTING-DEVICE-ID") String deviceId,
      @Valid @RequestBody RegisterRequest request) {
    return authService.register(request, deviceId);
  }

  @PostMapping("/send-verification-number")
  public void sendVerificationNumber(
      @RequestHeader("X-FLIRTING-DEVICE-ID") String deviceId,
      @RequestBody SendVerifyEmailRequest request) {
    authService.sendVerificationNumber(request.email(), deviceId);
  }

  @PostMapping("/verify-email")
  public void verifyEmail(
      @RequestHeader("X-FLIRTING-DEVICE-ID") String deviceId,
      @Valid @RequestBody VerifyEmailRequest request) {
    authService.verifyEmail(request.email(), deviceId, request.verificationNumber());
  }
}
