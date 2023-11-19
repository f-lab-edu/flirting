package site.ymango.match;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import site.ymango.match.model.MatchAcceptEvent;
import site.ymango.match.model.MatchRequestEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchEventListener {
  private final KafkaTemplate<String, MatchRequestEvent> matchRequestKafkaTemplate;
  private final KafkaTemplate<String, MatchAcceptEvent> matchAcceptKafkaTemplate;

  @Async
  @EventListener
  public void handleMatchCreateEvent(MatchRequestEvent matchRequestEvent) {
    matchRequestKafkaTemplate.send("match_request", matchRequestEvent);
    log.info("MatchRequestEvent. matchRequestEvent: {}", matchRequestEvent);
  }

  @Async
  @EventListener
  public void handleMatchAcceptEvent(MatchAcceptEvent matchAcceptEvent) {
    matchAcceptKafkaTemplate.send("match_accept", matchAcceptEvent);
    log.info("MatchAcceptEvent. matchAcceptEvent: {}", matchAcceptEvent);
  }
}
