package site.ymango.email;

import feign.Feign;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.ymango.email.client.OutboundMailerClient;
import site.ymango.email.interceptor.OutboundMailerHeaderInjectInterceptor;

@Configuration
@RequiredArgsConstructor
public class OutboundMailerClientConfiguration {

  @Value("${outbound-mailer.endpoint}")
  private String endpoint;

  @Bean
  public OutboundMailerClient outboundMailerClient() {
    return Feign.builder()
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .requestInterceptor(outboundMailerHeaderInjectInterceptor())
        .target(OutboundMailerClient.class, endpoint);
  }

  @Bean
  public RequestInterceptor outboundMailerHeaderInjectInterceptor() {
    return new OutboundMailerHeaderInjectInterceptor();
  }
}
