package site.ymango.user.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.ymango.user.dto.UserResponse;
import site.ymango.user.service.UserViewService;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserViewService userViewService;

  @GetMapping("/me")
  public UserResponse signup(
      @RequestHeader("Authorization") String token
  ) {
    return userViewService.getUser(token);
  }

}
