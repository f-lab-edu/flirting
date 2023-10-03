package site.ymango.enums;

import lombok.Getter;

@Getter
public enum NotificationTemplate {
  VERIFY_EMAIL(SendType.EMAIL, "10880"),
  ;

  private final SendType sendType;
  private final String templateId;

  NotificationTemplate(SendType sendType, String templateId) {
    this.sendType = sendType;
    this.templateId = templateId;
  }
}
