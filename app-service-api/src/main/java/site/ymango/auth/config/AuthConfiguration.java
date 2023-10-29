package site.ymango.auth.config;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import site.ymango.user.UserService;
import site.ymango.user.model.User;

@Configuration
@RequiredArgsConstructor
public class AuthConfiguration {
  private final UserService userService;
  private final Environment environment;

  @Bean
  public UserDetailsService userDetailsService() {
    return email -> {
      User user = userService.getUser(email);
      return org.springframework.security.core.userdetails.User.builder()
          .username(user.email())
          .password(user.password())
          .build();
    };
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    return request -> {
      var cors = new org.springframework.web.cors.CorsConfiguration();
      cors.setAllowCredentials(true);
      if (Arrays.asList(environment.getActiveProfiles()).contains("local")) {
        cors.setAllowedOrigins(List.of("http://localhost:3000"));
      }
      cors.addAllowedHeader("*");
      cors.addAllowedMethod("*");
      return cors;
    };
  }
}
