package site.ymango.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.company.CompanyService;
import site.ymango.exception.BaseException;
import site.ymango.exception.ErrorCode;
import site.ymango.user.entity.UserEntity;
import site.ymango.user.entity.UserProfileEntity;
import site.ymango.user.enums.Mbti;
import site.ymango.user.model.UserProfile;
import site.ymango.user.repository.UserProfileRepository;
import site.ymango.user.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileService {
  private final UserProfileRepository userProfileRepository;
  private final CompanyService companyService;
  private final UserRepository userRepository;
  private final ObjectMapper objectMapper;

  @Transactional(readOnly = true)
  public UserProfile getUserProfile(Long userProfileId) {
    UserProfileEntity userProfileEntity = userProfileRepository.findById(userProfileId).orElseThrow(
        () -> new BaseException(ErrorCode.USER_PROFILE_NOT_FOUND));
    return UserProfile.builder()
        .userProfileId(userProfileEntity.getUserProfileId())
        .username(userProfileEntity.getUsername())
        .gender(userProfileEntity.getGender())
        .birthdate(userProfileEntity.getBirthdate())
        .sido(userProfileEntity.getSido())
        .sigungu(userProfileEntity.getSigungu())
        .mbti(userProfileEntity.getMbti())
        .preferMbti(userProfileEntity.getPreferMbti())
        .location(userProfileEntity.getLocation())
        .createdAt(userProfileEntity.getCreatedAt())
        .updatedAt(userProfileEntity.getUpdatedAt())
        .deletedAt(userProfileEntity.getDeletedAt())
        .userCompany(companyService.getCompany(userProfileEntity.getCompanyId()))
        .build();
  }

  @Transactional(readOnly = true)
  public UserProfile getRecommendUserProfile(Long userId) {
    UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

    Pattern pattern = Pattern.compile(userEntity.getUserProfile().getPreferMbti().name().replaceAll("O", "."));
    List<Mbti> preferredMbti = Arrays.stream(Mbti.values()).filter(mbti -> pattern.matcher(mbti.name()).matches()).collect(Collectors.toList());

    UserProfileEntity userProfileEntity = userProfileRepository.findRecommendProfile(userEntity.getUserId(),
        userEntity.getUserProfile().getLocation(), userEntity.getUserProfile().getGender(), preferredMbti,
        userEntity.getUserProfile().getBirthdate()).orElseThrow(
        () -> {
          // TODO: 더이상 추천할 프로필이 없는 경우 푸시 알림
          log.info("추천 프로필이 없습니다. uid: {}", userId);
          return new BaseException(ErrorCode.USER_PROFILE_NOT_FOUND);
        });

    return objectMapper.convertValue(userProfileEntity, UserProfile.class);
  }

}
