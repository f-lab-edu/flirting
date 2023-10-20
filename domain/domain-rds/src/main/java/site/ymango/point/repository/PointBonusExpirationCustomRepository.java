package site.ymango.point.repository;

import java.time.LocalDateTime;
import java.util.List;
import site.ymango.point.entity.PointBonusExpirationEntity;

public interface PointBonusExpirationCustomRepository {

  /**
   * 만료처리되지 않은 보너스 포인트 조회
   * @param walletId
   * @param now
   * @return
   */
  List<PointBonusExpirationEntity> findNotExpiredBonusPoint(Long walletId, LocalDateTime now);
}
