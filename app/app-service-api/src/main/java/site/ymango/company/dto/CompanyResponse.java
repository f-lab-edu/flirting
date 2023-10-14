package site.ymango.company.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CompanyResponse(
    Integer companyId,
    String name,
    String domain,
    String iconUrl,
    LocalDateTime createdAt
) {

}
