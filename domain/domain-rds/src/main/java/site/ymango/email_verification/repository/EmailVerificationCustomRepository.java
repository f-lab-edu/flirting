package site.ymango.email_verification.repository;

import java.util.Optional;
import site.ymango.email_verification.entity.EmailVerificationEntity;

public interface EmailVerificationCustomRepository {

  boolean exists(String email, String deviceId, String verificationNumber, boolean verified);

  boolean exists(String email, String deviceId, boolean verified);

  Optional<EmailVerificationEntity> findOne(String email, String deviceId, String verificationNumber, boolean verified);

}
