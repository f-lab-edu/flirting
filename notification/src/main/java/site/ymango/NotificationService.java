package site.ymango;

import java.util.Map;
import site.ymango.enums.NotificationTemplate;
import site.ymango.enums.SendType;


public interface NotificationService {
   void send(String recipient, NotificationTemplate template, Map<String, Object> parameters);

   boolean support(SendType type);
}
