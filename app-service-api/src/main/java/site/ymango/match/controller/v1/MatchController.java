package site.ymango.match.controller.v1;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.ymango.advice.AuthorizationAttribute;
import site.ymango.match.MatchService;
import site.ymango.match.dto.MatchReceivedResponse;
import site.ymango.match.dto.MatchRequestResponse;
import site.ymango.match.model.MatchAcceptEvent;
import site.ymango.match.model.MatchRequestEvent;

@RestController
@RequestMapping("/v1/matches")
@RequiredArgsConstructor
public class MatchController {
  private final MatchService matchService;
  private final ApplicationEventPublisher eventPublisher;

  @GetMapping("/requests")
  public List<MatchRequestResponse> getRequestMatches() {
    return matchService.getRequestMatches(AuthorizationAttribute.getUserId())
        .stream()
        .map(r -> MatchRequestResponse.builder()
            .matchRequestId(r.matchRequestId())
            .targetUserId(r.targetUserId())
            .createdAt(r.createdAt())
            .expiredAt(r.expiredAt())
            .build())
        .collect(Collectors.toList());
  }

  @PostMapping("/requests")
  public void createMatchRequest(@RequestParam Long targetUserId) {
    eventPublisher.publishEvent(MatchRequestEvent.builder()
        .userId(AuthorizationAttribute.getUserId())
        .targetUserId(targetUserId)
        );
  }

  @PostMapping("/requests/{matchRequestId}/accept")
  public void acceptMatchRequest(@PathVariable Long matchRequestId, @RequestParam Long requestUserId) {
    eventPublisher.publishEvent(MatchAcceptEvent.builder()
        .userId(AuthorizationAttribute.getUserId())
        .matchRequestId(matchRequestId)
        .requestUserId(requestUserId)
        .build());
  }

  @DeleteMapping("/requests/{matchRequestId}")
  public void removeMatchRequest(@PathVariable Long matchRequestId) {
    matchService.removeMatchRequest(AuthorizationAttribute.getUserId(), matchRequestId);
  }

  @GetMapping("/received")
  public List<MatchReceivedResponse> getReceivedMatches() {
    return matchService.getReceivedMatches(AuthorizationAttribute.getUserId())
    .stream()
        .map(r -> MatchReceivedResponse.builder()
            .matchRequestId(r.matchRequestId())
            .targetUserId(r.targetUserId())
            .createdAt(r.createdAt())
            .expiredAt(r.expiredAt())
            .build())
        .collect(Collectors.toList());
  }


}
