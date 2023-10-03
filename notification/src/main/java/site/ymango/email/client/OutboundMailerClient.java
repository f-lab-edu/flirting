package site.ymango.email.client;

import feign.Headers;
import feign.RequestLine;
import site.ymango.email.dto.request.body.SendMailRequestBody;
import site.ymango.email.dto.response.SendEmailResponse;

@Headers({"Content-Type: application/json", "Accept: application/json"})
public interface OutboundMailerClient {

  @RequestLine("POST /api/v1/mails")
  SendEmailResponse sendEmail(SendMailRequestBody body);
}
