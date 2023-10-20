package site.ymango.point.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.ymango.point.entity.PointWalletEntity;

public interface PointWalletRepository extends JpaRepository<PointWalletEntity, Long>, PointWalletCustomRepository {

  Optional<PointWalletEntity> findByUserId(Long userId);

  boolean existsByUserId(Long userId);
}
