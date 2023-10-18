package site.ymango.email_verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.email_verification.entity.EmailVerificationEntity;
import site.ymango.email_verification.repository.EmailVerificationRepository;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

  private final EmailVerificationRepository emailVerificationRepository;

  @Transactional
  public void createEmailVerification(String email, String deviceId, String verificationNumber) {
    if (emailVerificationRepository.exists(email, deviceId, verificationNumber, false)) {
      return;
    }

    EmailVerificationEntity emailVerification = EmailVerificationEntity.builder()
        .email(email)
        .deviceId(deviceId)
        .verificationNumber(verificationNumber)
        .build();

    emailVerificationRepository.save(emailVerification);
  }

  @Transactional
  public boolean verifyEmail(String email, String deviceId, String verificationNumber) {
    EmailVerificationEntity emailVerification = emailVerificationRepository.findOne(email,
        deviceId, verificationNumber, false).orElseThrow(
        () -> new BaseException(ErrorCode.EMAIL_VERIFICATION_NOT_FOUND));

    emailVerification.verify();
    emailVerificationRepository.save(emailVerification);
    return true;
  }

  @Transactional(readOnly = true)
  public boolean isVerified(String email, String deviceId, boolean verified) {
    return emailVerificationRepository.exists(email, deviceId, verified);
  }
}
