package site.ymango.match.model;

import lombok.Builder;

@Builder
public record MatchAcceptEvent(
        Long userId,
        Long matchRequestId,
        Long requestUserId
) {

}
