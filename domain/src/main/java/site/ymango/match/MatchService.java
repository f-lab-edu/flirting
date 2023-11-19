package site.ymango.match;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;
import site.ymango.match.entity.MatchRequestEntity;
import site.ymango.match.model.MatchRequest;
import site.ymango.match.repository.MatchRequestRepository;
import site.ymango.recommend_profile.RecommendProfileService;
import site.ymango.recommend_profile.model.RecommendProfile;

@Service
@RequiredArgsConstructor
public class MatchService {
  private final MatchRequestRepository matchRequestRepository;
  private final RecommendProfileService recommendProfileService;

  @Transactional
  public void createMatchRequest(long userId, long recommendProfileId) {
    RecommendProfile recommendProfile = recommendProfileService.getRecommendProfile(userId, recommendProfileId);

    MatchRequestEntity matchRequestEntity = MatchRequestEntity.builder()
        .userId(userId)
        .targetUserId(recommendProfile.userProfile().userId())
        .accepted(Boolean.FALSE)
        .expiredAt(LocalDateTime.now().plusDays(7))
        .build();
    matchRequestRepository.save(matchRequestEntity);
  }

  @Transactional
  public void acceptMatchRequest(long userId, long matchRequestId, long requestUserId) {
     MatchRequestEntity matchRequestEntity = matchRequestRepository.findByMatchRequestIdAndUserIdAndTargetUserIdAndAccepted(matchRequestId, requestUserId, userId, Boolean.FALSE)
        .orElseThrow(() -> new BaseException(ErrorCode.MATCH_REQUEST_NOT_FOUND));
    matchRequestEntity.accept();
  }

  @Transactional
  public void removeMatchRequest(long userId, long matchRequestId) {
    MatchRequestEntity matchRequestEntity = matchRequestRepository.findByMatchRequestIdAndUserIdAndAccepted(matchRequestId, userId, Boolean.FALSE)
        .orElseThrow(() -> new BaseException(ErrorCode.MATCH_REQUEST_NOT_FOUND));
    matchRequestEntity.remove();
  }

  @Transactional(readOnly = true)
  public List<MatchRequest> getRequestMatches(long userId) {
    return matchRequestRepository.findByUserIdAndAccepted(userId, Boolean.FALSE)
        .stream()
        .map(r -> MatchRequest.builder()
            .matchRequestId(r.getMatchRequestId())
            .targetUserId(r.getTargetUserId())
            .accepted(r.getAccepted())
            .expiredAt(r.getExpiredAt())
            .build())
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<MatchRequest> getReceivedMatches(long userId) {
    return matchRequestRepository.findByTargetUserIdAndAccepted(userId, Boolean.FALSE)
        .stream()
        .map(r -> MatchRequest.builder()
            .matchRequestId(r.getMatchRequestId())
            .targetUserId(r.getTargetUserId())
            .accepted(r.getAccepted())
            .expiredAt(r.getExpiredAt())
            .build())
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<MatchRequest> getMatchedUsers(long userId) {
    return matchRequestRepository.findByUserIdAndAccepted(userId, Boolean.TRUE)
        .stream()
        .map(r -> MatchRequest.builder()
            .matchRequestId(r.getMatchRequestId())
            .targetUserId(r.getTargetUserId())
            .accepted(r.getAccepted())
            .expiredAt(r.getExpiredAt())
            .build())
        .collect(Collectors.toList());
  }
}
