package site.ymango.company.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.ymango.company.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer>, CompanyCustomRepository {
  boolean existsByDomain(String domain);
}
