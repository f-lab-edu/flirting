package site.ymango.recommend_profile.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import site.ymango.user.dto.UserResponse;

@Builder
public record RecommendProfileResponse(
    Long recommendProfileId,
    Long userId,
    UserResponse userProfile,
    Integer rating,
    LocalDateTime createdAt
) {

}
