package site.ymango.match.model;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MatchRequest(
    Long matchRequestId,
    Long userId,
    Long targetUserId,
    Boolean accepted,
    LocalDateTime createdAt,
    LocalDateTime expiredAt
) {

}
