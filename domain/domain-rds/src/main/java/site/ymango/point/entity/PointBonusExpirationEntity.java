package site.ymango.point.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "point_bonus_expiration", catalog = "service", indexes = {
    @Index(name = "point_bonus_expiration_ix_point_wallet_id", columnList = "point_wallet_id"),
})
public class PointBonusExpirationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "point_bonus_expiration_id")
  private Long pointBonusExpirationId;

  @Column(name = "point_wallet_id")
  private Long pointWalletId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "expired_at")
  private LocalDateTime expiredAt;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
