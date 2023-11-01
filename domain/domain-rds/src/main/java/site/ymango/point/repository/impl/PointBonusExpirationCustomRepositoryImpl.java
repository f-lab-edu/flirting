package site.ymango.point.repository.impl;

import static site.ymango.point.entity.QPointBonusExpirationEntity.pointBonusExpirationEntity;
import static site.ymango.point.entity.QPointEventEntity.pointEventEntity;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.ymango.point.entity.PointBonusExpirationEntity;
import site.ymango.point.enums.EventType;
import site.ymango.point.repository.PointBonusExpirationCustomRepository;

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
                JPAExpressions.select(pointEventEntity.bonusPointExpirationId)
                    .from(pointEventEntity)
                    .where(
                        pointEventEntity.pointWalletId.eq(walletId),
                        pointEventEntity.eventType.eq(EventType.EXPIRATION)
                    )
            )
        )
        .fetch();
  }

}
