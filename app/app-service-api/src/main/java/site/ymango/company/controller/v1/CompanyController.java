package site.ymango.company.controller.v1;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.ymango.company.CompanyService;
import site.ymango.company.dto.CompanyResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/companies")
public class CompanyController {
  private final CompanyService companyService;

  @GetMapping
  public List<CompanyResponse> getCompanies() {
    return companyService.getCompanies().stream()
        .map(userCompany -> new CompanyResponse(
            userCompany.companyId(),
            userCompany.name(),
            userCompany.domain(),
            userCompany.iconUrl(),
            userCompany.createdAt()
        ))
        .toList();
  }
}
