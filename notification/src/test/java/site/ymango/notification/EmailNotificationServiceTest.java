package site.ymango.notification;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import site.ymango.email.EmailNotificationService;
import site.ymango.email.dto.response.SendEmailResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailNotificationServiceTest {
  @Autowired
  private EmailNotificationService emailNotificationService;

  @Test
  void sendEmail() {
//    SendEmailResponse response = emailNotificationService.send();
//    assertNotNull(response);
//    assertEquals(1, response.getCount());
  }
}