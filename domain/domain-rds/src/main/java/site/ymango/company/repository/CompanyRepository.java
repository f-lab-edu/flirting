package site.ymango.company.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.company.entity.CompanyEntity;

@Repository
@Transactional(readOnly = true)
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
  Optional<CompanyEntity> findByDomain(String domain);
}
