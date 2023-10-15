package site.ymango.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "email_verification", catalog = "service", indexes = {
    @Index(name = "email_verification_ix_email", columnList = "email"),
})
public class EmailVerificationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "email_verification_id")
  private Long emailVerificationId;

  @Column(name = "email")
  private String email;

  @Column(name = "verified")
  @Builder.Default
  private boolean verified = false;

  @Column(name = "verification_number")
  private String verificationNumber;

  @Column(name = "device_id")
  private String deviceId;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public void verify() {
    this.verified = true;
  }
}
