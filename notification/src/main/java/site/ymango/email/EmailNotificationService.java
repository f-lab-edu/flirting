package site.ymango.email;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.ymango.NotificationService;
import site.ymango.email.client.OutboundMailerClient;
import site.ymango.email.dto.request.body.SendMailRequestBody;
import site.ymango.email.model.Recipient;
import site.ymango.enums.NotificationTemplate;
import site.ymango.enums.SendType;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService implements NotificationService {
  private final OutboundMailerClient outboundMailerClient;

  @Override
  public void send(String email, NotificationTemplate template, Map<String, Object> parameters) {
    Recipient recipient = Recipient.builder()
        .address(email)
        .parameters(parameters)
        .build();

    SendMailRequestBody sendMailRequestBody = SendMailRequestBody.builder()
        .templateSid(template.getTemplateId())
        .recipients(List.of(recipient))
        .build();

    outboundMailerClient.sendEmail(sendMailRequestBody);
  }

  @Override
  public boolean support(SendType type) {
    return type == SendType.EMAIL;
  }
}
