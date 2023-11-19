package site.ymango.match.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MatchRequestResponse(
        long matchRequestId,
        long userId,
        long targetUserId,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
) {

}
