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
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "point_wallet", catalog = "service", uniqueConstraints = {
    @UniqueConstraint(name = "point_wallet_uq_user_id", columnNames = {"user_id"})
})
public class PointWalletEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "point_wallet_id")
  private Long pointWalletId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "point")
  @Builder.Default
  private Integer point = 0;

  @Column(name = "bonus_point")
  @Builder.Default
  private Integer bonusPoint = 0;

  @Version
  @Column(name = "version")
  private Integer version;

  @CreatedDate
  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public PointWalletEntity addPoint(Integer point) {
    this.point += point;
    return this;
  }

  public PointWalletEntity addBonusPoint(Integer bonusPoint) {
    this.bonusPoint += bonusPoint;
    return this;
  }

  public PointWalletEntity usePoint(Integer point) {
    if (this.point + this.bonusPoint < point) {
      throw new BaseException(ErrorCode.POINT_NOT_ENOUGH);
    }

    if (this.bonusPoint > 0) {
      this.bonusPoint -= point;
      if (this.bonusPoint < 0) {
        this.point += this.bonusPoint;
        this.bonusPoint = 0;
      }
    } else {
      this.point -= point;
    }
    return this;
  }
}
