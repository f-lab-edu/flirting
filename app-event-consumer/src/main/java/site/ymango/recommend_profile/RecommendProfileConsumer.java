package site.ymango.recommend_profile;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendProfileConsumer {
  private final RecommendProfileService profileRecommendService;

  @KafkaListener(topics = "profile_recommend", groupId = "profile_recommend_group")
  public void listener(Long userId) {
    profileRecommendService.createRecommendProfile(userId);
  }
}
