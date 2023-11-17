package site.ymango.recommend_profile.entity;

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
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at IS NULL AND expired_at > NOW()")
@Table(name = "recommend_profile", indexes = {
    @Index(name = "recommend_profile_ix_user_id", columnList = "user_id")
})
@SQLDelete(sql = "UPDATE recommend_profile SET deleted_at = NOW() WHERE recommend_profile_id = ?")
public class RecommendProfileEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recommend_profile_id")
  private Long recommendProfileId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_profile_id")
  private Long userProfileId;

  @Column(name = "rating")
  private Integer rating;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "expired_at")
  private LocalDateTime expiredAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public void rate(Integer rating) {
    this.rating = rating;
  }
}
