package site.ymango.email.enums;

import lombok.Getter;

@Getter
public enum EmailTemplate {
  VERIFY_EMAIL("10880"),
  ;

  private final String templateId;

  EmailTemplate(String templateId) {
    this.templateId = templateId;
  }
}
