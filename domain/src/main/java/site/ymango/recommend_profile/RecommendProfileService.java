package site.ymango.recommend_profile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;
import site.ymango.point.PointService;
import site.ymango.point.enums.EventType;
import site.ymango.recommend_profile.entity.RecommendProfileEntity;
import site.ymango.recommend_profile.model.RecommendProfile;
import site.ymango.recommend_profile.repository.RecommendProfileRepository;
import site.ymango.user.UserProfileService;
import site.ymango.user.model.UserProfile;

@Service
@RequiredArgsConstructor
public class RecommendProfileService {
  private final RecommendProfileRepository recommendProfileRepository;
  private final UserProfileService userProfileService;
  private final PointService pointService;

  @Transactional(readOnly = true)
  public List<RecommendProfile> getRecommendProfiles(Long userId) {
    List<RecommendProfileEntity> recommendProfiles = recommendProfileRepository.findByUserId(userId);

    return recommendProfiles.stream()
        .map(p -> RecommendProfile.builder()
            .recommendProfileId(p.getRecommendProfileId())
            .userId(p.getUserId())
            .createdAt(p.getCreatedAt())
            .expiredAt(p.getExpiredAt())
            .userProfile(userProfileService.getUserProfile(p.getUserProfileId()))
            .build()).collect(Collectors.toList());
  }

  /**
   * 포인트 사용 프로필 추천
   * @param userId
   */
  @Transactional
  public void createRecommendProfileByPoint(Long userId) {
    pointService.usePoint(userId, EventType.RECOMMEND_PROFILE);
    createRecommendProfile(userId);
  }

  /**
   * 무료 프로필 추천
   * @param userId
   */
  @Transactional
  public void createRecommendProfile(Long userId) {
    UserProfile recommendUserProfile = userProfileService.getRecommendUserProfile(userId);

    RecommendProfileEntity recommendProfile = RecommendProfileEntity.builder()
        .userId(userId)
        .userProfileId(recommendUserProfile.userProfileId())
        .expiredAt(LocalDateTime.now().plusDays(7))
        .build();
    recommendProfileRepository.save(recommendProfile);
  }

  @Transactional
  public void deleteRecommendProfile(Long userId, Long recommendProfileId) {
    RecommendProfileEntity recommendProfile = recommendProfileRepository.findByRecommendProfileIdAndUserId(recommendProfileId, userId)
        .orElseThrow(() -> new BaseException(ErrorCode.RECOMMEND_PROFILE_NOT_FOUND));
    recommendProfileRepository.delete(recommendProfile);
  }

  @Transactional
  public void rateRecommendProfile(Long userId, Long recommendProfileId, Integer rating) {
    RecommendProfileEntity recommendProfile = recommendProfileRepository.findByRecommendProfileIdAndUserId(recommendProfileId, userId)
        .orElseThrow(() -> new BaseException(ErrorCode.RECOMMEND_PROFILE_NOT_FOUND));
    recommendProfile.rate(rating);
    recommendProfileRepository.save(recommendProfile);
  }
}
