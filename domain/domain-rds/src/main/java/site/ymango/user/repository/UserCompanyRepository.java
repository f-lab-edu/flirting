package site.ymango.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.ymango.user.entity.UserCompanyEntity;

@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompanyEntity, Integer> {
  Optional<UserCompanyEntity> findByDomain(String domain);
}
