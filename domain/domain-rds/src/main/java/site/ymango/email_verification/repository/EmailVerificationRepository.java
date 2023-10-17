package site.ymango.email_verification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ymango.email_verification.entity.EmailVerificationEntity;

public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Long>, EmailVerificationCustomRepository {

}
