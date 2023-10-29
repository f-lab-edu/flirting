package site.ymango.user.model;

import lombok.Builder;

@Builder
public record UserCreateEvent(
    Long userId
) {
}
