package site.ymango.point.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.ymango.point.enums.EventType;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "point_event", catalog = "service", indexes = {
    @Index(name = "point_event_ix_point_wallet_id", columnList = "point_wallet_id"),
})
public class PointEventEntity {
  @Id
  @Column(name = "point_event_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pointEventId;

  @Column(name = "point_wallet_id")
  private Long pointWalletId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "bonus_point_expiration_id")
  private Long bonusPointExpirationId;

  @Column(name = "event_type")
  private EventType eventType;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
