package site.ymango.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.ymango.user.dto.UserCompanyResponse;
import site.ymango.user.dto.UserResponse;
import site.ymango.user.service.UserViewService;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserViewService userViewService;
  private final UserService userService;

  @GetMapping("/me")
  public UserResponse signup(
      @RequestHeader("Authorization") String token
  ) {
    return userViewService.getUser(token);
  }

  @GetMapping("/companies")
  public List<UserCompanyResponse> getUserCompanies(
  ) {
    return userService.getUserCompanies().stream()
        .map(userCompany -> new UserCompanyResponse(
            userCompany.userCompanyId(),
            userCompany.name(),
            userCompany.domain(),
            userCompany.iconUrl(),
            userCompany.createdAt()
        ))
        .toList();
  }

}
