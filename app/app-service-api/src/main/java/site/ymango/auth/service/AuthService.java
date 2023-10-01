package site.ymango.auth.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.ymango.auth.dto.request.AuthenticationRequest;
import site.ymango.auth.dto.request.RegisterRequest;
import site.ymango.auth.dto.response.AuthenticationResponse;
import site.ymango.auth.provider.JwtService;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;
import site.ymango.user.UserService;
import site.ymango.user.model.Location;
import site.ymango.user.model.User;
import site.ymango.user.model.UserCompany;
import site.ymango.user.model.UserProfile;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
    } catch (AuthenticationException e) {
      throw new BaseException(ErrorCode.AUTHENTICATION_FAILED, e.getLocalizedMessage());
    }

    User user = userService.getUser(request.email());
    String token = jwtService.generateToken(user);

    return new AuthenticationResponse(token);
  }

  public AuthenticationResponse register(RegisterRequest request) {
    String companyDomain = request.email().split("@")[1];

    User createdUser = userService.create(User.builder()
        .email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .userProfile(UserProfile.builder()
            .username(request.username())
            .gender(request.gender())
            .mbti(request.mbti())
            .birthdate(request.birthdate())
            .preferMbti(request.preferMbti())
            .sido(request.sido())
            .sigungu(request.sigungu())
            .location(new Location(request.latitude(), request.longitude()))
            .userCompany(UserCompany.builder()
                .domain(companyDomain)
                .build())
            .build())
        .build());

    String token = jwtService.generateToken(createdUser);

    return new AuthenticationResponse(token);
  }
}
