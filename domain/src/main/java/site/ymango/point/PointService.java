package site.ymango.point;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;
import site.ymango.point.entity.PointBonusExpirationEntity;
import site.ymango.point.entity.PointHistoryEntity;
import site.ymango.point.entity.PointWalletEntity;
import site.ymango.point.enums.ReferenceType;
import site.ymango.point.enums.TransactionType;
import site.ymango.point.repository.PointBonusExpirationRepository;
import site.ymango.point.repository.PointHistoryRepository;
import site.ymango.point.repository.PointWalletRepository;

@Service
@RequiredArgsConstructor
public class PointService {

  private final PointWalletRepository pointRepository;
  private final PointHistoryRepository pointHistoryRepository;

  private final PointBonusExpirationRepository pointBonusExpirationRepository;

  public void createPointWallet(Long userId) {
    if (pointRepository.existsByUserId(userId)) {
      throw new BaseException(ErrorCode.POINT_WALLET_ALREADY_EXISTS);
    }

    pointRepository.save(PointWalletEntity.builder()
        .userId(userId)
        .build());
  }

  @Transactional
  public void addPoint(Long userId, Integer point, ReferenceType referenceType, Long referenceId) {
    PointWalletEntity pointWallet = pointRepository.findByUserId(userId).orElseThrow(() -> new BaseException(ErrorCode.POINT_WALLET_NOT_FOUND))
        .addPoint(point);

    pointRepository.save(pointWallet);

    pointHistoryRepository.save(PointHistoryEntity.builder()
        .userId(userId)
        .amount(point)
        .pointWalletId(pointWallet.getPointWalletId())
        .currentPoint(pointWallet.getPoint())
        .currentBonusPoint(pointWallet.getBonusPoint())
        .transactionType(TransactionType.ADD_POINT)
        .referenceType(referenceType)
        .referenceId(referenceId)
        .build());
  }

  @Transactional
  public void addBonusPoint(Long userId, Integer bonusPoint, LocalDateTime expiredAt) {
    PointWalletEntity pointWallet = pointRepository.findByUserId(userId).orElseThrow(() -> new BaseException(ErrorCode.POINT_WALLET_NOT_FOUND))
        .addBonusPoint(bonusPoint);

    pointRepository.save(pointWallet);

    PointBonusExpirationEntity pointBonusExpiration = pointBonusExpirationRepository.save(PointBonusExpirationEntity.builder()
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
        .referenceType(ReferenceType.EXPIRE_BONUS_POINT)
        .referenceId(pointBonusExpiration.getPointBonusExpirationId())
        .build());
  }

  @Transactional
  public void usePoint(Long userId, Integer point, ReferenceType referenceType, Long referenceId) {
    PointWalletEntity pointWallet = settleBonusPoint(
        pointRepository.findByUserId(userId).orElseThrow(() -> new BaseException(ErrorCode.POINT_WALLET_NOT_FOUND))).usePoint(point);

    pointRepository.save(pointWallet);

    pointHistoryRepository.save(PointHistoryEntity.builder()
        .userId(userId)
        .pointWalletId(pointWallet.getPointWalletId())
        .amount(point)
        .currentPoint(pointWallet.getPoint())
        .currentBonusPoint(pointWallet.getBonusPoint())
        .transactionType(TransactionType.USE_POINT)
        .referenceType(referenceType)
        .referenceId(referenceId)
        .build());
  }

  /**
   * 보너스 포인트 정산 - 만료기한 지난 보너스 포인트 처분
   *
   * @param pointWallet
   */
  private PointWalletEntity settleBonusPoint(PointWalletEntity pointWallet) {
    pointBonusExpirationRepository.findNotExpiredBonusPoint(pointWallet.getPointWalletId(), LocalDateTime.now())
        .stream().map(pointBonusExpiration -> {
          pointWallet.usePoint(pointBonusExpiration.getAmount());
          return PointHistoryEntity.builder()
              .userId(pointWallet.getUserId())
              .pointWalletId(pointWallet.getPointWalletId())
              .currentPoint(pointWallet.getPoint())
              .amount(pointBonusExpiration.getAmount())
              .currentBonusPoint(pointWallet.getBonusPoint())
              .transactionType(TransactionType.EXPIRE_BONUS_POINT)
              .referenceType(ReferenceType.EXPIRE_BONUS_POINT)
              .referenceId(pointBonusExpiration.getPointBonusExpirationId())
              .build();
        }).forEach(pointHistoryRepository::save);
    return pointRepository.save(pointWallet);
  }
}