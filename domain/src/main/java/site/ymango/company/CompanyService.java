package site.ymango.company;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.company.entity.CompanyEntity;
import site.ymango.company.repository.CompanyRepository;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;
import site.ymango.user.model.Company;

@Service
@RequiredArgsConstructor
public class CompanyService {
  private final CompanyRepository companyRepository;

  @Transactional(readOnly = true)
  public List<Company> getCompanies(String keyword, Pageable pageable) {
    return companyRepository.findByName(keyword, pageable).stream()
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

  @Transactional(readOnly = true)
  public Company getCompany(Integer companyId) {
    CompanyEntity companyEntity = companyRepository.findByCompanyId(companyId)
        .orElseThrow(() -> new BaseException(ErrorCode.COMPANY_NOT_FOUND));

    return Company.builder()
        .companyId(companyEntity.getCompanyId())
        .name(companyEntity.getName())
        .domain(companyEntity.getDomain())
        .iconUrl(companyEntity.getIconUrl())
        .createdAt(companyEntity.getCreatedAt())
        .updatedAt(companyEntity.getUpdatedAt())
        .deletedAt(companyEntity.getDeletedAt())
        .build();
  }

  @Transactional(readOnly = true)
  public Company getCompany(String domain) {
    CompanyEntity companyEntity = companyRepository.findByDomain(domain)
        .orElseThrow(() -> new BaseException(ErrorCode.COMPANY_NOT_FOUND));

    return Company.builder()
        .companyId(companyEntity.getCompanyId())
        .name(companyEntity.getName())
        .domain(companyEntity.getDomain())
        .iconUrl(companyEntity.getIconUrl())
        .createdAt(companyEntity.getCreatedAt())
        .updatedAt(companyEntity.getUpdatedAt())
        .deletedAt(companyEntity.getDeletedAt())
        .build();
  }
}
