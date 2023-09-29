package site.ymango.user.model;

import java.time.LocalDateTime;

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
