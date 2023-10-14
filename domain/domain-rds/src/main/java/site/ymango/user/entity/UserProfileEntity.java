package site.ymango.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.ymango.company.entity.CompanyEntity;
import site.ymango.user.converter.LocationConverter;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.enums.PerferMbti;
import site.ymango.user.model.Location;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"userEntity"})
@Table(name = "user_profile", catalog = "service")
@Where(clause = "deleted_at IS NULL")
@EntityListeners(AuditingEntityListener.class)
public class UserProfileEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_profile_id")
  private Long userProfileId;

  @Column(name = "username")
  private String username;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "birthdate")
  private LocalDate birthdate;

  @Column(name = "sido")
  private String sido;

  @Column(name = "sigungu")
  private String sigungu;

  @Column(name = "mbti")
  @Enumerated(EnumType.STRING)
  private Mbti mbti;

  @Column(name = "prefer_mbti")
  @Enumerated(EnumType.STRING)
  private PerferMbti preferMbti;

  @Column(name = "location")
  @Convert(converter = LocationConverter.class)
  private Location location;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @OneToOne(mappedBy = "userProfile")
  private UserEntity userEntity;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity company;
}
