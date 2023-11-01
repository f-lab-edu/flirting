package site.ymango;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendConsumer {
  @KafkaListener(topics = "recommend_profile")
  public void listener(Long userId) {
    System.out.println("쿠폰 생성에 성공하였습니다. userId: " + userId);
  }
}
