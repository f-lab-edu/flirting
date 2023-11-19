package site.ymango.match.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MatchReceivedResponse(
        long matchRequestId,
        long userId,
        long targetUserId,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
) {

}
