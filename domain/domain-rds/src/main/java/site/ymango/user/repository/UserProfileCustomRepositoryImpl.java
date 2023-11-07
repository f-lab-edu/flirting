package site.ymango.user.repository;

import static site.ymango.recommend_profile.entity.QRecommendProfileEntity.recommendProfileEntity;
import static site.ymango.user.entity.QUserProfileEntity.userProfileEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.ymango.user.entity.UserProfileEntity;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.model.Location;

@Repository
@RequiredArgsConstructor
public class UserProfileCustomRepositoryImpl implements UserProfileCustomRepository {
  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<UserProfileEntity> findRecommendProfile(Long userId, Location location, Gender gender, List<Mbti> preferredMbti, LocalDate birthdate) {
    return Optional.ofNullable(queryFactory.selectFrom(userProfileEntity)
        .where(
            distanceLessThanOne10Km(location),
            notInRecommendProfileBetweenYears(userId, LocalDateTime.now().minusMonths(6)),
            userProfileEntity.gender.eq(gender == Gender.MALE ? Gender.FEMALE : Gender.MALE),
            userProfileEntity.mbti.in(preferredMbti),
            userProfileEntity.birthdate.between(birthdate.minusYears(10), birthdate.plusYears(10))
        ).fetchOne());
  }

  private BooleanExpression distanceLessThanOne10Km(Location location) {
    return Expressions.booleanTemplate("ST_Distance_Sphere(location, POINT({0}, {1})) < {2}",
        location.longitude(), location.latitude(), 1000000);
  }

  private BooleanExpression notInRecommendProfileBetweenYears(Long userId, LocalDateTime recommendedAt) {
    return userProfileEntity.userProfileId.notIn(
        JPAExpressions
            .select(recommendProfileEntity.userProfileId)
            .from(recommendProfileEntity)
            .where(recommendProfileEntity.userId.eq(userId)
                .and(recommendProfileEntity.createdAt.after(recommendedAt.minusMonths(6)))
    ));
  }
}
