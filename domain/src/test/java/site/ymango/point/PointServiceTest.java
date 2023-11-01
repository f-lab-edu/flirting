package site.ymango.point;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.ymango.DatabaseClearExtension;
import site.ymango.exception.BaseException;
import site.ymango.point.entity.PointWalletEntity;
import site.ymango.point.enums.EventType;
import site.ymango.point.repository.PointWalletRepository;

@SpringBootTest
@ExtendWith(DatabaseClearExtension.class)
class PointServiceTest {
  @Autowired
  private PointService pointService;

  @Autowired
  private PointWalletRepository pointWalletRepository;

  @Test
  @DisplayName("포인트 지갑 생성")
  void test() {
    // when
    pointService.createPointWallet(1L);

    // then
    assertTrue(pointWalletRepository.existsByUserId(1L));
  }

  @Test
  @DisplayName("포인트 지갑 중복 생성")
  void test2() {
    pointService.createPointWallet(1L);
    assertThrows(BaseException.class, () -> pointService.createPointWallet(1L));
  }

  @Test
  @DisplayName("포인트 적립")
  void test3() {
    // given
    pointService.createPointWallet(1L);

    // when
    pointService.addPoint(1L, 100, EventType.PURCHASE);

    // then
    PointWalletEntity pointWallet = pointWalletRepository.findByUserId(1L).orElseThrow(
        () -> new RuntimeException("포인트 지갑이 생성되지 않았습니다."));
    assertEquals(100, pointWallet.getPoint());
    assertEquals(0, pointWallet.getBonusPoint());
  }

  @Test
  @DisplayName("포인트 적립 - 포인트 지갑 없음")
  void test4() {
    assertThrows(BaseException.class, () -> pointService.addPoint(1L, 100, EventType.PURCHASE));
  }

  @Test
  @DisplayName("포인트 사용 - 기본 포인트")
  void test5() {
    // given
    pointService.createPointWallet(1L);
    pointService.addPoint(1L, 100, EventType.USER_SIGN_UP);

    // when
    pointService.usePoint(1L, 50, EventType.RECOMMEND_PROFILE);

    // then
    PointWalletEntity pointWallet = pointWalletRepository.findByUserId(1L).orElseThrow(
        () -> new RuntimeException("포인트 지갑이 생성되지 않았습니다."));
    assertEquals(50, pointWallet.getPoint());
    assertEquals(0, pointWallet.getBonusPoint());
  }

  @Test
  @DisplayName("포인트 사용 - 보너스 포인트, 보너스 포인트 먼저 사용")
  void test6() {
    // given
    pointService.createPointWallet(1L);
    pointService.addPoint(1L, 100, EventType.PURCHASE);
    pointService.addBonusPoint(1L, 100, LocalDateTime.now().plusDays(1), EventType.USER_SIGN_UP);

    // when
    pointService.usePoint(1L, 50, EventType.RECOMMEND_PROFILE);

    // then
    PointWalletEntity pointWallet = pointWalletRepository.findByUserId(1L).orElseThrow(
        () -> new RuntimeException("포인트 지갑이 생성되지 않았습니다."));
    assertEquals(100, pointWallet.getPoint());
    assertEquals(50, pointWallet.getBonusPoint());
  }

  @Test
  @DisplayName("포인트 사용 - 보너스 포인트 만료")
  void test7() {
    // given
    pointService.createPointWallet(1L);
    pointService.addPoint(1L, 100, EventType.PURCHASE);
    pointService.addBonusPoint(1L, 100, LocalDateTime.now().minusDays(1), EventType.USER_SIGN_UP);

    // when
    pointService.usePoint(1L, 50, EventType.RECOMMEND_PROFILE);

    // then
    PointWalletEntity pointWallet = pointWalletRepository.findByUserId(1L).orElseThrow(
        () -> new RuntimeException("포인트 지갑이 생성되지 않았습니다."));
    assertEquals(50, pointWallet.getPoint());
    assertEquals(0, pointWallet.getBonusPoint());
  }
}