package site.ymango.email_verification.repository;

import static site.ymango.email_verification.entity.QEmailVerificationEntity.emailVerificationEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.ymango.email_verification.entity.EmailVerificationEntity;

@Repository
@RequiredArgsConstructor
public class EmailVerificationCustomRepositoryImpl implements EmailVerificationCustomRepository {
  private final JPAQueryFactory queryFactory;

  public boolean exists(String email, String deviceId, String verificationNumber, boolean verified) {
    Integer fetchFirst = queryFactory.selectOne()
        .from(emailVerificationEntity)
        .where(emailVerificationEntity.email.eq(email)
            .and(emailVerificationEntity.deviceId.eq(deviceId))
            .and(emailVerificationEntity.verificationNumber.eq(verificationNumber))
            .and(emailVerificationEntity.verified.eq(verified)))
        .fetchFirst();

    return fetchFirst != null;
  }

  public boolean exists(String email, String deviceId, boolean verified) {
    Integer fetchFirst = queryFactory.selectOne()
        .from(emailVerificationEntity)
        .where(emailVerificationEntity.email.eq(email)
            .and(emailVerificationEntity.deviceId.eq(deviceId))
            .and(emailVerificationEntity.verified.eq(verified)))
        .fetchFirst();

    return fetchFirst != null;
  }

  public Optional<EmailVerificationEntity> findOne(String email,String deviceId,String verificationNumber,boolean verified){
    EmailVerificationEntity result = queryFactory.selectFrom(emailVerificationEntity)
        .where(emailVerificationEntity.email.eq(email)
            .and(emailVerificationEntity.deviceId.eq(deviceId))
            .and(emailVerificationEntity.verificationNumber.eq(verificationNumber))
            .and(emailVerificationEntity.verified.eq(verified)))
        .fetchOne();
    return Optional.ofNullable(result);
  }
}
