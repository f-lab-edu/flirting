package site.ymango.point.repository;

import java.util.List;
import site.ymango.point.entity.PointHistoryEntity;

public interface PointHistoryCustomRepository {
  List<PointHistoryEntity> findNotExpiredBonusPoint(Long pointWalletId);
}
