package site.ymango.user.model;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Company(
    Integer companyId,
    String name,
    String domain,
    String iconUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}
