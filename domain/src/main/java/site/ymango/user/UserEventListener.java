package site.ymango.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import site.ymango.user.model.UserCreateEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {
  private final KafkaTemplate<String, Long> kafkaTemplate;

  @Async
  @EventListener
  public void handleUserCreateEvent(UserCreateEvent userCreateEvent) {
    kafkaTemplate.send("user_create", userCreateEvent.userId());
    log.info("UserCreateEvent. userId: {}", userCreateEvent.userId());
  }
}
