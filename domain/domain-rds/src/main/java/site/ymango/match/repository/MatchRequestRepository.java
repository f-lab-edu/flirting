package site.ymango.match.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.ymango.match.entity.MatchRequestEntity;

@Repository
public interface MatchRequestRepository extends JpaRepository<MatchRequestEntity, Long> {
  List<MatchRequestEntity> findByUserIdAndAccepted(Long userId, Boolean accepted);

  List<MatchRequestEntity> findByTargetUserIdAndAccepted(Long targetUserId, Boolean accepted);
  Optional<MatchRequestEntity> findByMatchRequestIdAndUserIdAndTargetUserIdAndAccepted(Long matchRequestId, Long userId, Long targetUserId, Boolean accepted);

  Optional<MatchRequestEntity> findByMatchRequestIdAndUserIdAndAccepted(Long userId, Long targetUserId, Boolean accepted);
}
