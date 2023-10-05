package site.ymango.user.model;

import java.time.LocalDateTime;
import lombok.Builder;
import site.ymango.user.enums.UserStatus;

@Builder
public record User(
    Long userId,
    String email,
    String password,
    UserStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime closedAt,
    UserProfile userProfile
) {

}
