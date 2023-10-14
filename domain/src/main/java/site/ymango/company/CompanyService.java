package site.ymango.company;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.ymango.company.repository.CompanyRepository;
import site.ymango.user.model.Company;

@Service
@RequiredArgsConstructor
public class CompanyService {
  private final CompanyRepository userCompanyRepository;

  public List<Company> getCompanies(String keyword, Pageable pageable) {
    return userCompanyRepository.findByName(keyword, pageable).stream()
        .map(company -> Company.builder()
            .companyId(company.getCompanyId())
            .name(company.getName())
            .domain(company.getDomain())
            .iconUrl(company.getIconUrl())
            .createdAt(company.getCreatedAt())
            .updatedAt(company.getUpdatedAt())
            .deletedAt(company.getDeletedAt())
            .build())
        .collect(Collectors.toList());
  }
}
