package site.ymango.point.repository;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.ymango.point.entity.PointWallet;

@SpringBootTest
class PointWalletRepositoryTest {
  @Autowired
  private PointWalletRepository pointWalletRepository;

  @Test
  void test() {
    PointWallet wallet = PointWallet.builder()
        .walletId("1")
        .userId(1L)
        .point(100)
        .bonusPoint(0)
        .build();

    pointWalletRepository.save(wallet);

    Optional<PointWallet> byUserId = pointWalletRepository.findByUserId(1L);

    System.out.println(byUserId);
  }
}