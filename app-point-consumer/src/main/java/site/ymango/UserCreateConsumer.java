package site.ymango;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import site.ymango.point.PointService;

@Component
@RequiredArgsConstructor
public class UserCreateConsumer {
  private final PointService pointService;

  @KafkaListener(topics = "user_create", groupId = "group_1")
  public void userCreateListener(Long userId) {
    pointService.createPointWallet(userId);
  }
}
