package site.ymango.recommend;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;
import site.ymango.recommend.entity.RecommendProfileEntity;
import site.ymango.recommend.enums.RecommendType;
import site.ymango.recommend.model.RecommendProfile;
import site.ymango.recommend.repository.RecommendProfileRepository;
import site.ymango.user.UserService;

@Service
@RequiredArgsConstructor
public class RecommendProfileService {
  private final RecommendProfileRepository recommendProfileRepository;
  private final UserService userService;

  @Transactional(readOnly = true)
  public List<RecommendProfile> getRecommendProfiles(Long userId) {
    List<RecommendProfileEntity> recommendProfiles = recommendProfileRepository.findByUserId(userId);

    return recommendProfiles.stream()
        .map(p -> RecommendProfile.builder()
            .recommendProfileId(p.getRecommendProfileId())
            .userId(p.getUserId())
            .createdAt(p.getCreatedAt())
            .expiredAt(p.getExpiredAt())
            .userProfile(userService.getUserProfile(p.getUserProfileId()))
            .build()).collect(Collectors.toList());
  }

  @Transactional
  public void createRecommendProfile(Long userId, Long userProfileId, RecommendType recommendType) {
    RecommendProfileEntity recommendProfile = RecommendProfileEntity.builder()
        .userId(userId)
        .userProfileId(userProfileId)
        .recommendType(recommendType)
        .expiredAt(LocalDateTime.now().plusDays(7))
        .build();
    recommendProfileRepository.save(recommendProfile);
  }

  @Transactional
  public void deleteRecommendProfile(Long recommendProfileId) {
    RecommendProfileEntity recommendProfile = recommendProfileRepository.findById(recommendProfileId)
        .orElseThrow(() -> new BaseException(ErrorCode.RECOMMEND_PROFILE_NOT_FOUND));
    recommendProfileRepository.delete(recommendProfile);
  }

  @Transactional
  public void rateRecommendProfile(Long recommendProfileId, Integer rating) {
    RecommendProfileEntity recommendProfile = recommendProfileRepository.findById(recommendProfileId)
        .orElseThrow(() -> new BaseException(ErrorCode.RECOMMEND_PROFILE_NOT_FOUND));
    recommendProfile.rate(rating);
    recommendProfileRepository.save(recommendProfile);
  }
}
