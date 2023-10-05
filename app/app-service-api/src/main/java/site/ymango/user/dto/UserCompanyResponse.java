package site.ymango.user.dto;

import java.time.LocalDateTime;

public record UserCompanyResponse(
    Integer userCompanyId,
    String name,
    String domain,
    String iconUrl,
    LocalDateTime createdAt
) {

}
