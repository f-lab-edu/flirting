package site.ymango.recommend_profile.controller.v1;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.*;
import site.ymango.advice.AuthorizationAttribute;
import site.ymango.recommend_profile.RecommendProfileService;
import site.ymango.recommend_profile.dto.RecommendProfileResponse;
import site.ymango.user.dto.UserResponse;

@RestController
@RequestMapping("/v1/recommend-profiles")
@RequiredArgsConstructor
public class RecommendProfileController {
  private final RecommendProfileService recommendProfileService;

  @GetMapping
  public List<RecommendProfileResponse> getRecommendProfiles() {
    return recommendProfileService.getRecommendProfiles(AuthorizationAttribute.getUserId())
        .stream().map(i -> RecommendProfileResponse.builder()
            .recommendProfileId(i.recommendProfileId())
            .userId(i.userId())
            .userProfile(UserResponse.builder()
                .username(i.userProfile().username())
                .birthdate(i.userProfile().birthdate())
                .gender(i.userProfile().gender())
                .mbti(i.userProfile().mbti())
                .companyName(i.userProfile().userCompany().name())
                .companyIconUrl(i.userProfile().userCompany().iconUrl())
                .preferMbti(i.userProfile().preferMbti())
                .sido(i.userProfile().sido())
                .sigungu(i.userProfile().sigungu())
                .build())
            .createdAt(i.createdAt())
            .build()).collect(Collectors.toList());
  }

  @PostMapping
  public List<RecommendProfileResponse> createRecommendProfile() {
    recommendProfileService.createRecommendProfileByPoint(AuthorizationAttribute.getUserId());
    return getRecommendProfiles();
  }

  @PutMapping("/{recommendProfileId}/rating")
  public void updateRating(@PathVariable Long recommendProfileId, @RequestParam @Range(min = 1, max = 5) Integer rating) {
    recommendProfileService.rateRecommendProfile(AuthorizationAttribute.getUserId(), recommendProfileId, rating);
  }

  @DeleteMapping("/{recommendProfileId}")
  public void deleteRecommendProfile(@PathVariable Long recommendProfileId) {
    recommendProfileService.deleteRecommendProfile(AuthorizationAttribute.getUserId(), recommendProfileId);
  }
}