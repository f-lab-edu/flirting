package site.ymango.company.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.ymango.company.entity.CompanyEntity;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
  Optional<CompanyEntity> findByDomain(String domain);

  Optional<CompanyEntity> findByCompanyId(Integer companyId);

  @Query("SELECT c FROM CompanyEntity c WHERE :name IS NULL OR c.name LIKE %:name%")
  List<CompanyEntity> findByName(@Param("name") String keyword, Pageable pageable);
}
