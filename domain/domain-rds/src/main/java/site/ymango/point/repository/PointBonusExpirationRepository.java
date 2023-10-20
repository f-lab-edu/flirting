package site.ymango.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ymango.point.entity.PointBonusExpirationEntity;

public interface PointBonusExpirationRepository extends JpaRepository<PointBonusExpirationEntity, Long>, PointBonusExpirationCustomRepository {

}
