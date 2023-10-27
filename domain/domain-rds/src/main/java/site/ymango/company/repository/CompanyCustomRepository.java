package site.ymango.company.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.ymango.company.entity.CompanyEntity;

public interface CompanyCustomRepository {

  Optional<CompanyEntity> findByDomain(String domain);

  Optional<CompanyEntity> findByCompanyId(Integer companyId);

  Page<CompanyEntity> findByName(String keyword, Pageable pageable);
}
