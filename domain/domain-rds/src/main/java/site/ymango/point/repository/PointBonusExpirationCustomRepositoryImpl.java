package site.ymango.point.repository;

import static site.ymango.point.entity.QPointBonusExpirationEntity.pointBonusExpirationEntity;
import static site.ymango.point.entity.QPointHistoryEntity.pointHistoryEntity;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.ymango.point.entity.PointBonusExpirationEntity;
import site.ymango.point.enums.TransactionType;

@Repository
@RequiredArgsConstructor
public class PointBonusExpirationCustomRepositoryImpl implements PointBonusExpirationCustomRepository {

  private final JPAQueryFactory queryFactory;

  public List<PointBonusExpirationEntity> findNotExpiredBonusPoint(Long walletId, LocalDateTime now) {
    return queryFactory.selectFrom(pointBonusExpirationEntity)
        .where(
            pointBonusExpirationEntity.pointWalletId.eq(walletId),
            pointBonusExpirationEntity.expiredAt.before(now),
            pointBonusExpirationEntity.pointBonusExpirationId.notIn(
                JPAExpressions.select(pointHistoryEntity.referenceId)
                    .from(pointHistoryEntity)
                    .where(
                        pointHistoryEntity.pointWalletId.eq(walletId),
                        pointHistoryEntity.transactionType.eq(TransactionType.EXPIRE_BONUS_POINT)
                    )
            )
        )
        .fetch();
  }

}
