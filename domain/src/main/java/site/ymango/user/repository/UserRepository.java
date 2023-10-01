package site.ymango.user.repository;

import java.util.Optional;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.ymango.user.entity.UserEntity;

@Repository
@Where(clause = "closed_at IS NULL")
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  boolean existsByEmail(String email);

  Optional<UserEntity> findByEmail(String email);
}
