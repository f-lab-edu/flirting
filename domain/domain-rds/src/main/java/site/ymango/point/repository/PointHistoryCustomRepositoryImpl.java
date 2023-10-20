package site.ymango.point.repository;


import static site.ymango.point.entity.QPointHistoryEntity.pointHistoryEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.ymango.point.entity.PointHistoryEntity;
import site.ymango.point.enums.TransactionType;

@Repository
@RequiredArgsConstructor
public class PointHistoryCustomRepositoryImpl implements PointHistoryCustomRepository {
  private final JPAQueryFactory queryFactory;

  public List<PointHistoryEntity> findNotExpiredBonusPoint(Long pointWalletId) {
    return queryFactory.select(pointHistoryEntity)
        .from(pointHistoryEntity)
        .leftJoin(pointHistoryEntity)
        .on(pointHistoryEntity.pointHistoryId.eq(pointHistoryEntity.referenceId))
        .where(pointHistoryEntity.pointWalletId.eq(pointWalletId))
        .where(pointHistoryEntity.transactionType.eq(TransactionType.ADD_BONUS_POINT))
        .where(pointHistoryEntity.referenceId.isNull())
        .fetch();
  }
}
