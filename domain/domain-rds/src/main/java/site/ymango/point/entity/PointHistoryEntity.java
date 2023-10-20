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
import site.ymango.point.enums.ReferenceType;
import site.ymango.point.enums.TransactionType;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "point_history", catalog = "service", indexes = {
    @Index(name = "point_history_ix_point_wallet_id", columnList = "point_wallet_id"),
})
public class PointHistoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "point_history_id")
  private Long pointHistoryId;

  @Column(name = "point_wallet_id")
  private Long pointWalletId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "transaction_type")
  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "current_point")
  private Integer currentPoint;

  @Column(name = "current_bonus_point")
  private Integer currentBonusPoint;

  @Column(name = "reference_type")
  @Enumerated(EnumType.STRING)
  private ReferenceType referenceType;

  @Column(name = "reference_id")
  private Long referenceId;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
