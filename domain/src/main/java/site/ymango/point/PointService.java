package site.ymango.point;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;
import site.ymango.point.entity.PointBonusExpirationEntity;
import site.ymango.point.entity.PointEventEntity;
import site.ymango.point.entity.PointHistoryEntity;
import site.ymango.point.entity.PointWalletEntity;
import site.ymango.point.enums.EventType;
import site.ymango.point.enums.TransactionType;
import site.ymango.point.repository.PointBonusExpirationRepository;
import site.ymango.point.repository.PointEventRepository;
import site.ymango.point.repository.PointHistoryRepository;
import site.ymango.point.repository.PointWalletRepository;

@Service
@RequiredArgsConstructor
public class PointService {

  private final PointWalletRepository pointRepository;
  private final PointHistoryRepository pointHistoryRepository;
  private final PointEventRepository pointEventRepository;

  private final PointBonusExpirationRepository pointBonusExpirationRepository;

  @Transactional
  public void createPointWallet(Long userId) {
    if (pointRepository.existsByUserId(userId)) {
      throw new BaseException(ErrorCode.POINT_WALLET_ALREADY_EXISTS);
    }

    pointRepository.save(PointWalletEntity.builder()
        .userId(userId)
        .build());
  }

  @Transactional
  public void addPoint(Long userId, Integer point, EventType eventType) {
    PointWalletEntity pointWallet = pointRepository.findByUserId(userId).orElseThrow(() -> new BaseException(ErrorCode.POINT_WALLET_NOT_FOUND))
        .addPoint(point);

    pointRepository.save(pointWallet);

    pointEventRepository.save(PointEventEntity.builder()
            .userId(userId)
            .pointWalletId(pointWallet.getPointWalletId())
            .eventType(eventType)
            .build());

    pointHistoryRepository.save(PointHistoryEntity.builder()
        .userId(userId)
        .amount(point)
        .pointWalletId(pointWallet.getPointWalletId())
        .currentPoint(pointWallet.getPoint())
        .currentBonusPoint(pointWallet.getBonusPoint())
        .transactionType(TransactionType.ADD_POINT)
        .build());
  }

  @Transactional
  public void addBonusPoint(Long userId, Integer bonusPoint, LocalDateTime expiredAt, EventType eventType) {
    PointWalletEntity pointWallet = pointRepository.findByUserId(userId).orElseThrow(() -> new BaseException(ErrorCode.POINT_WALLET_NOT_FOUND))
        .addBonusPoint(bonusPoint);

    pointRepository.save(pointWallet);

    pointEventRepository.save(PointEventEntity.builder()
        .userId(userId)
        .pointWalletId(pointWallet.getPointWalletId())
        .eventType(eventType)
        .build());

    pointBonusExpirationRepository.save(PointBonusExpirationEntity.builder()
        .pointWalletId(pointWallet.getPointWalletId())
        .userId(userId)
        .amount(bonusPoint)
        .expiredAt(expiredAt)
        .build());

    pointHistoryRepository.save(PointHistoryEntity.builder()
        .userId(userId)
        .amount(bonusPoint)
        .pointWalletId(pointWallet.getPointWalletId())
        .currentPoint(pointWallet.getPoint())
        .currentBonusPoint(pointWallet.getBonusPoint())
        .transactionType(TransactionType.ADD_BONUS_POINT)
        .build());
  }

  @Transactional
  public void usePoint(Long userId, Integer point, EventType eventType) {
    PointWalletEntity pointWallet = settleBonusPoint(
        pointRepository.findByUserId(userId).orElseThrow(() -> new BaseException(ErrorCode.POINT_WALLET_NOT_FOUND))).usePoint(point);

    pointRepository.save(pointWallet);

    pointEventRepository.save(PointEventEntity.builder()
        .userId(userId)
        .pointWalletId(pointWallet.getPointWalletId())
        .eventType(eventType)
        .build());

    pointHistoryRepository.save(PointHistoryEntity.builder()
        .userId(userId)
        .pointWalletId(pointWallet.getPointWalletId())
        .amount(point)
        .currentPoint(pointWallet.getPoint())
        .currentBonusPoint(pointWallet.getBonusPoint())
        .transactionType(TransactionType.USE_POINT)
        .build());
  }

  /**
   * 보너스 포인트 정산 - 만료기한 지난 보너스 포인트 처분
   *
   * @param pointWallet
   */
  private PointWalletEntity settleBonusPoint(PointWalletEntity pointWallet) {
    pointBonusExpirationRepository.findNotExpiredBonusPoint(pointWallet.getPointWalletId(), LocalDateTime.now())
        .forEach(pointBonusExpiration -> {
          pointWallet.usePoint(pointBonusExpiration.getAmount());
          pointHistoryRepository.save(PointHistoryEntity.builder()
              .userId(pointWallet.getUserId())
              .pointWalletId(pointWallet.getPointWalletId())
              .currentPoint(pointWallet.getPoint())
              .amount(pointBonusExpiration.getAmount())
              .currentBonusPoint(pointWallet.getBonusPoint())
              .transactionType(TransactionType.EXPIRE_BONUS_POINT)
              .build());
          pointEventRepository.save(PointEventEntity.builder()
              .userId(pointWallet.getUserId())
              .pointWalletId(pointWallet.getPointWalletId())
              .eventType(EventType.EXPIRATION)
              .bonusPointExpirationId(pointBonusExpiration.getPointBonusExpirationId())
              .build());
        });
    return pointRepository.save(pointWallet);
  }
}