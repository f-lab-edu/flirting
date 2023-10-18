package site.ymango.notification;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.ymango.email.EmailNotificationService;
import site.ymango.enums.NotificationTemplate;

@SpringBootTest
class EmailNotificationServiceTest {
  @Autowired
  private EmailNotificationService emailNotificationService;

  @Test
  void sendEmail() {
    String email = "corn@heybit.io";
    NotificationTemplate template = NotificationTemplate.EMAIL_VERIFICATION;
    Map<String, Object> parameters = Map.of("verificationNumber", "1234");

    emailNotificationService.send(email, template, parameters);
  }
}