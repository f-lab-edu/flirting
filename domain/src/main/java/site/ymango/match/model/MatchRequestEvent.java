package site.ymango.match.model;

import lombok.Builder;

@Builder
public record MatchRequestEvent(
        Long userId,
        Long targetUserId
) {

}
