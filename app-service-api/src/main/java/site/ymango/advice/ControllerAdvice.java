package site.ymango.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import site.ymango.auth.service.JwtService;
import site.ymango.user.UserService;
import site.ymango.user.model.User;

@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
public class ControllerAdvice {
  private final UserService userService;
  private final JwtService jwtService;

  @Around("execution(* site.ymango..*Controller.*(..))")
  public Object initRequest(ProceedingJoinPoint joinPoint) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    String authorization = request.getHeader("authorization");
    if (authorization == null || !authorization.startsWith("Bearer")) {
      return joinPoint.proceed();
    }

    String accessToken = authorization.replaceAll("Bearer", "").trim();
    String deviceId = request.getHeader("X-FLIRTING-DEVICE-ID");

    String userId = jwtService.extractUserId(accessToken);
    User user = userService.getUser(Long.valueOf(userId));

    AuthorizationAttribute.setAttribute("userId", user.userId());
    AuthorizationAttribute.setAttribute("deviceId", deviceId);

    Object proceed = joinPoint.proceed();
    AuthorizationAttribute.clear();
    return proceed;
  }
}
