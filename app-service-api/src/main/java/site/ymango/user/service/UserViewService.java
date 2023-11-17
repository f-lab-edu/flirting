package site.ymango.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ymango.auth.service.JwtService;
import site.ymango.user.UserService;
import site.ymango.user.dto.UserResponse;
import site.ymango.user.model.User;

@Service
@RequiredArgsConstructor
public class UserViewService {
  private final UserService userService;
  private final JwtService jwtService;

  public UserResponse getUser(String token) {
    String email = jwtService.extractUserId(token.substring(7));
    User user = userService.getUser(email);

    return new UserResponse(
        user.email(),
        user.userProfile().username(),
        user.userProfile().gender(),
        user.userProfile().birthdate(),
        user.userProfile().sido(),
        user.userProfile().sigungu(),
        user.userProfile().mbti(),
        user.userProfile().preferMbti(),
        user.userProfile().userCompany().name(),
        user.userProfile().userCompany().domain(),
        user.userProfile().userCompany().iconUrl()
    );
  }
}
