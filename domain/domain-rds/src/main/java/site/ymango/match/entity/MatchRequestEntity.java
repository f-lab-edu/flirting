package site.ymango.match.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at is null AND expired_at > now()")
@Table(name = "match_request", catalog = "service", indexes = {
    @Index(name = "match_request_ix_user_id", columnList = "user_id"),
    @Index(name = "match_request_ix_target_user_id", columnList = "target_user_id"),
})
@SQLDelete(sql = "UPDATE match_request SET deleted_at = NOW() WHERE match_request_id = ?")
public class MatchRequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long matchRequestId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "target_user_id")
  private Long targetUserId;

  @Column(name = "accepted")
  private Boolean accepted;

  @Column(name = "accepted_at")
  private LocalDateTime acceptedAt;

  @Column(name = "expired_at", nullable = true)
  private LocalDateTime expiredAt;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public void accept() {
    this.accepted = true;
    this.acceptedAt = LocalDateTime.now();
  }

  public void remove() {
    this.deletedAt = LocalDateTime.now();
  }
}
