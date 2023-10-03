package site.ymango;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import site.ymango.email.EmailNotificationService;
import site.ymango.enums.SendType;

@SpringBootTest
@ActiveProfiles("local")
class NotificationServiceFactoryTest {
  @Autowired
  private NotificationServiceFactory notificationServiceFactory;

  @Test
  @DisplayName("EmailNotificationService should be returned when SendType is EMAIL")
  void get() {
    NotificationService notificationService = notificationServiceFactory.get(SendType.EMAIL);
    assertNotNull(notificationService);
    assertEquals(EmailNotificationService.class, notificationService.getClass());
  }
}