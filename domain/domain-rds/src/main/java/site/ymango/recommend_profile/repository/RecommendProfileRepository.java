package site.ymango.recommend_profile.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import site.ymango.recommend_profile.entity.RecommendProfileEntity;

public interface RecommendProfileRepository extends JpaRepository<RecommendProfileEntity, Long>, RecommendProfileCustomRepository {

  List<RecommendProfileEntity> findByUserId(Long userId);
}
