package site.ymango.user.model;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserCompany(
    Integer userCompanyId,
    String name,
    String domain,
    String iconUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}
