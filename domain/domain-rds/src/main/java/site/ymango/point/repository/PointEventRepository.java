package site.ymango.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.ymango.point.entity.PointEventEntity;

@Repository
public interface PointEventRepository extends JpaRepository<PointEventEntity, Long> {
}
