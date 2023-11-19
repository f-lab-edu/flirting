package site.ymango.match;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import site.ymango.match.model.MatchAcceptEvent;
import site.ymango.match.model.MatchRequestEvent;

@Component
@RequiredArgsConstructor
public class MatchRequestConsumer {
  private final MatchService matchService;

  @KafkaListener(topics = "match_request", groupId = "match_request_group", containerFactory = "matchRequestEventConcurrentKafkaListenerContainerFactory")
  public void listener(MatchRequestEvent matchRequestEvent) {
    matchService.createMatchRequest(matchRequestEvent.userId(), matchRequestEvent.targetUserId());
  }

  @KafkaListener(topics = "match_accept", groupId = "match_accept_group", containerFactory = "matchAcceptEventConcurrentKafkaListenerContainerFactory")
  public void listener2(MatchAcceptEvent matchAcceptEvent) {
    matchService.acceptMatchRequest(matchAcceptEvent.userId(), matchAcceptEvent.matchRequestId(), matchAcceptEvent.requestUserId());
  }
}
