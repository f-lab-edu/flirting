package site.ymango.recommend_profile.model;

import java.time.LocalDateTime;
import lombok.Builder;
import site.ymango.user.model.UserProfile;

@Builder
public record RecommendProfile(
    Long recommendProfileId,
    Long userId,
    UserProfile userProfile,
    Integer rating,
    LocalDateTime createdAt,
    LocalDateTime expiredAt
) {

}
