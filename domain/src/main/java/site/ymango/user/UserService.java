package site.ymango.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.exception.ErrorCode;
import site.ymango.exception.BaseException;
import site.ymango.user.entity.EmailVerificationEntity;
import site.ymango.user.entity.UserCompanyEntity;
import site.ymango.user.entity.UserEntity;
import site.ymango.user.model.User;
import site.ymango.user.model.UserCompany;
import site.ymango.user.repository.EmailVerificationRepository;
import site.ymango.user.repository.UserCompanyRepository;
import site.ymango.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserCompanyRepository userCompanyRepository;
  private final EmailVerificationRepository emailVerificationRepository;
  private final ObjectMapper objectMapper;

  public User getUser(String email) {
    return objectMapper.convertValue(userRepository.findByEmail(email)
        .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND)), User.class);
  }

  public List<UserCompany> getUserCompanies() {
    return userCompanyRepository.findAll().stream()
        .map(userCompanyEntity -> objectMapper.convertValue(userCompanyEntity, UserCompany.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public User create(User user, String deviceId) {
    if (userRepository.existsByEmail(user.email())) {
      throw new BaseException(ErrorCode.USER_ALREADY_EXISTS);
    }

    if (!emailVerificationRepository.existsByEmailAndDeviceIdAndVerified(user.email(), deviceId, true)) {
      throw new BaseException(ErrorCode.EMAIL_NOT_VERIFIED);
    }

    UserCompanyEntity userCompanyEntity = userCompanyRepository.findByDomain(user.userProfile().userCompany().domain())
        .orElseThrow(() -> new BaseException(ErrorCode.COMPANY_NOT_FOUND));

    UserEntity userEntity = objectMapper.convertValue(user, UserEntity.class);

    userEntity.getUserProfile().setUserCompany(userCompanyEntity);

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
