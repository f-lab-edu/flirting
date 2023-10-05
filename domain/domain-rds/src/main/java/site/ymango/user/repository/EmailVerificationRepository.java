package site.ymango.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.ymango.user.entity.EmailVerificationEntity;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Integer> {
  boolean existsByEmailAndDeviceIdAndVerificationNumberAndVerified(String email, String deviceId, String verificationNumber, boolean verified);
  boolean existsByEmailAndDeviceIdAndVerified(String email, String deviceId, boolean verified);
  Optional<EmailVerificationEntity> findByEmailAndDeviceIdAndVerificationNumberAndVerified(String email, String deviceId, String verificationNumber, boolean verified);
}
