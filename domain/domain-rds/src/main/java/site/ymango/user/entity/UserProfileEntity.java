package site.ymango.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import site.ymango.user.enums.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.ymango.user.model.Location;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.ymango.company.entity.CompanyEntity;
import site.ymango.user.converter.LocationConverter;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"userEntity"})
@Table(name = "user_profile", catalog = "service", indexes = {
    @Index(name = "user_profile_ix_birthdate", columnList = "birthdate"),
})
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
