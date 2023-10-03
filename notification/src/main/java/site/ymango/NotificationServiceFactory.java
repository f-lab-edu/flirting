package site.ymango;

import java.util.List;
import org.springframework.stereotype.Component;
import site.ymango.enums.SendType;

@Component
public class NotificationServiceFactory {
  private final List<NotificationService> notificationServices;

  public NotificationServiceFactory(final List<NotificationService> notificationServices) {
    this.notificationServices = notificationServices;
  }

  public NotificationService get(SendType sendType) {
    return notificationServices.stream()
        .filter(notificationService -> notificationService.support(sendType))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No notification service found for send type: " + sendType));
  }
}
