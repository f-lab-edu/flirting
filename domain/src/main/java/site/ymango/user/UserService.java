package site.ymango.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.company.repository.CompanyRepository;
import site.ymango.exception.ErrorCode;
import site.ymango.exception.BaseException;
import site.ymango.user.entity.EmailVerificationEntity;
import site.ymango.company.entity.CompanyEntity;
import site.ymango.user.entity.UserEntity;
import site.ymango.user.entity.UserProfileEntity;
import site.ymango.user.model.User;
import site.ymango.user.repository.EmailVerificationRepository;
import site.ymango.user.repository.UserProfileRepository;
import site.ymango.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserProfileRepository userProfileRepository;
  private final CompanyRepository companyRepository;
  private final EmailVerificationRepository emailVerificationRepository;
  private final ObjectMapper objectMapper;

  public User getUser(String email) {
    return objectMapper.convertValue(userRepository.findByEmail(email)
        .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND)), User.class);
  }

  @Transactional
  public User create(User user, String deviceId) {
    if (!emailVerificationRepository.existsByEmailAndDeviceIdAndVerified(user.email(), deviceId, true)) {
      throw new BaseException(ErrorCode.EMAIL_NOT_VERIFIED);
    }

    if (userRepository.existsByEmail(user.email())) {
      throw new BaseException(ErrorCode.USER_ALREADY_EXISTS);
    }

    if (userProfileRepository.existsByUsername(user.userProfile().username())) {
      throw new BaseException(ErrorCode.USERNAME_ALREADY_EXISTS);
    }

    CompanyEntity companyEntity = companyRepository.findByDomain(user.userProfile().userCompany().domain())
        .orElseThrow(() -> new BaseException(ErrorCode.COMPANY_NOT_FOUND));

    UserEntity userEntity = UserEntity.builder()
        .email(user.email())
        .password(user.password())
        .userProfile(UserProfileEntity.builder()
            .username(user.userProfile().username())
            .gender(user.userProfile().gender())
            .birthdate(user.userProfile().birthdate())
            .sido(user.userProfile().sido())
            .sigungu(user.userProfile().sigungu())
            .location(user.userProfile().location())
            .mbti(user.userProfile().mbti())
            .preferMbti(user.userProfile().preferMbti())
            .company(companyEntity)
            .build()
        )
        .build();

    return objectMapper.convertValue(userRepository.save(userEntity), User.class);
  }

  public void createEmailVerification(String email, String deviceId, String verificationNumber) {
    if (emailVerificationRepository.existsByEmailAndDeviceIdAndVerificationNumberAndVerified(email, deviceId, verificationNumber, false)) {
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
    EmailVerificationEntity emailVerification = emailVerificationRepository.findByEmailAndDeviceIdAndVerificationNumberAndVerified(email,
        deviceId, verificationNumber, false).orElseThrow(
        () -> new BaseException(ErrorCode.EMAIL_VERIFICATION_NOT_FOUND));

    emailVerification.verify();
    emailVerificationRepository.save(emailVerification);
    return true;
  }
}
