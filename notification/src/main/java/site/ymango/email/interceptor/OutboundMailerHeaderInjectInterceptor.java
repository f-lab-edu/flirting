package site.ymango.email.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class OutboundMailerHeaderInjectInterceptor implements RequestInterceptor {
  @Value("${outbound-mailer.access-key}")
  private String accessKey;

  @Value("${outbound-mailer.secret-key}")
  private String secretKey;

  @Override
  public void apply(RequestTemplate template) {
    String timestamp = Long.toString(System.currentTimeMillis());
    template.header("x-ncp-apigw-timestamp", timestamp);
    template.header("x-ncp-iam-access-key", accessKey);
    template.header("x-ncp-lang", "ko-KR");
    try {
      template.header("x-ncp-apigw-signature-v2", makeSign(template, timestamp));
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
      e.printStackTrace();
    }
  }

  private String makeSign(RequestTemplate template, String timestamp) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
    String space = " ";  // 공백
    String newLine = "\n";  // 줄바꿈
    String method = template.method();  // HTTP 메소드
    String url = template.url();  // 도메인을 제외한 "/" 아래 전체 url (쿼리스트링 포함)

    log.info("method: {}", method);
    log.info("url: {}", url);

    String message = method
        + space
        + url
        + newLine
        + timestamp
        + newLine
        + accessKey;

    SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(signingKey);
    byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(rawHmac);
  }
}
