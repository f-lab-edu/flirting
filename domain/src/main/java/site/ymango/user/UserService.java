package site.ymango.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.company.CompanyService;
import site.ymango.email_verification.EmailVerificationService;
import site.ymango.exception.ErrorCode;
import site.ymango.exception.BaseException;
import site.ymango.user.entity.UserEntity;
import site.ymango.user.entity.UserProfileEntity;
import site.ymango.user.model.Company;
import site.ymango.user.model.User;
import site.ymango.user.model.UserProfile;
import site.ymango.user.repository.UserProfileRepository;
import site.ymango.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserProfileRepository userProfileRepository;
  private final CompanyService companyService;
  private final EmailVerificationService emailVerificationService;
  private final ObjectMapper objectMapper;

  @Transactional(readOnly = true)
  public User getUser(String email) {
    UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
        () -> new BaseException(ErrorCode.USER_NOT_FOUND));

  return User.builder()
      .userId(userEntity.getUserId())
      .password(userEntity.getPassword())
      .email(userEntity.getEmail())
      .status(userEntity.getStatus())
      .createdAt(userEntity.getCreatedAt())
      .updatedAt(userEntity.getUpdatedAt())
      .closedAt(userEntity.getClosedAt())
      .userProfile(UserProfile.builder()
          .userProfileId(userEntity.getUserProfile().getUserProfileId())
          .username(userEntity.getUserProfile().getUsername())
          .gender(userEntity.getUserProfile().getGender())
          .birthdate(userEntity.getUserProfile().getBirthdate())
          .sido(userEntity.getUserProfile().getSido())
          .sigungu(userEntity.getUserProfile().getSigungu())
          .mbti(userEntity.getUserProfile().getMbti())
          .preferMbti(userEntity.getUserProfile().getPreferMbti())
          .location(userEntity.getUserProfile().getLocation())
          .createdAt(userEntity.getUserProfile().getCreatedAt())
          .updatedAt(userEntity.getUserProfile().getUpdatedAt())
          .deletedAt(userEntity.getUserProfile().getDeletedAt())
          .userCompany(companyService.getCompany(userEntity.getUserProfile().getCompanyId()))
          .build())
      .build();
  }

  @Transactional
  public User create(User user, String deviceId) {
    if (!emailVerificationService.isVerified(user.email(), deviceId, true)) {
      throw new BaseException(ErrorCode.EMAIL_NOT_VERIFIED);
    }

    if (userRepository.existsByEmail(user.email())) {
      throw new BaseException(ErrorCode.USER_ALREADY_EXISTS);
    }

    if (userProfileRepository.existsByUsername(user.userProfile().username())) {
      throw new BaseException(ErrorCode.USERNAME_ALREADY_EXISTS);
    }

    Company company = companyService.getCompany(user.userProfile().userCompany().domain());

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
            .companyId(company.companyId())
            .build()
        )
        .build();

    return objectMapper.convertValue(userRepository.save(userEntity), User.class);
  }
}
