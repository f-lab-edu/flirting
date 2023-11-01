package site.ymango.recommend;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.ymango.DatabaseClearExtension;
import site.ymango.company.CompanyService;
import site.ymango.email_verification.EmailVerificationService;
import site.ymango.recommend.enums.RecommendType;
import site.ymango.recommend.model.RecommendProfile;
import site.ymango.recommend.repository.RecommendProfileRepository;
import site.ymango.user.UserService;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.enums.PerferMbti;
import site.ymango.user.enums.UserStatus;
import site.ymango.user.model.Company;
import site.ymango.user.model.Location;
import site.ymango.user.model.User;
import site.ymango.user.model.UserProfile;

@SpringBootTest
@ExtendWith(DatabaseClearExtension.class)
class RecommendProfileServiceTest {
  @Autowired
  private RecommendProfileService recommendProfileService;

  @Autowired
  private UserService userService;

  @Autowired
  private CompanyService companyService;

  @Autowired
  private EmailVerificationService emailVerificationService;

  @Autowired
  private RecommendProfileRepository recommendProfileRepository;

  @Test
  @DisplayName("추천 프로필 생성")
  void test01() {
    // given
    Long userId = 1L;
    Long userProfileId = 1L;

    // when
    recommendProfileService.createRecommendProfile(userId, userProfileId, RecommendType.DAILY_RECOMMEND);

    // then
    assertEquals(1, recommendProfileRepository.count());
  }

  @Test
  @DisplayName("추천 프로필 조회 - 성공")
  void test02() {
    // given
    String email = "test2@test.com";
    String password = "1234";
    Gender gender = Gender.MALE;
    String username = "username";
    LocalDate birthdate = LocalDate.now();
    String sido = "서울";
    String sigungu = "강남구";
    Mbti mbti = Mbti.INFJ;
    PerferMbti preferMbti = PerferMbti.IOFJ;
    Location location = new Location(1.0, 2.0);
    String deviceId = "test_device_id";
    String verificationNumber = "1234";

    Company company = new Company(
        null, "구글", "gmail.com", null, null, null, null
    );
    UserProfile userProfile = new UserProfile(
        null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );
    companyService.createCompany(company);
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);
    userService.create(user, deviceId);
    User createdUser = userService.getUser(email);

    // when
    recommendProfileService.createRecommendProfile(createdUser.userId(), createdUser.userProfile().userProfileId(), RecommendType.DAILY_RECOMMEND);

    // then
    var recommendProfiles = recommendProfileService.getRecommendProfiles(createdUser.userId());
    assertEquals(1, recommendProfiles.size());
  }

  @Test
  @DisplayName("추천 프로필 삭제")
  void test03() {
    // given
    String email = "test2@test.com";
    String password = "1234";
    Gender gender = Gender.MALE;
    String username = "username";
    LocalDate birthdate = LocalDate.now();
    String sido = "서울";
    String sigungu = "강남구";
    Mbti mbti = Mbti.INFJ;
    PerferMbti preferMbti = PerferMbti.IOFJ;
    Location location = new Location(1.0, 2.0);
    String deviceId = "test_device_id";
    String verificationNumber = "1234";

    Company company = new Company(
        null, "구글", "gmail.com", null, null, null, null
    );
    UserProfile userProfile = new UserProfile(
        null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );
    companyService.createCompany(company);
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);
    userService.create(user, deviceId);
    User createdUser = userService.getUser(email);
    recommendProfileService.createRecommendProfile(createdUser.userId(), createdUser.userProfile().userProfileId(), RecommendType.DAILY_RECOMMEND);

    // when
    var recommendProfiles = recommendProfileService.getRecommendProfiles(createdUser.userId());
    RecommendProfile recommendProfile = recommendProfiles.get(0);
    recommendProfileService.deleteRecommendProfile(recommendProfile.recommendProfileId());

    // then
    assertEquals(0, recommendProfileRepository.count());
  }

  @Test
  @DisplayName("추천 프로필 평가")
  void test04() {
    // given
    String email = "test2@test.com";
    String password = "1234";
    Gender gender = Gender.MALE;
    String username = "username";
    LocalDate birthdate = LocalDate.now();
    String sido = "서울";
    String sigungu = "강남구";
    Mbti mbti = Mbti.INFJ;
    PerferMbti preferMbti = PerferMbti.IOFJ;
    Location location = new Location(1.0, 2.0);
    String deviceId = "test_device_id";
    String verificationNumber = "1234";

    Company company = new Company(
        null, "구글", "gmail.com", null, null, null, null
    );
    UserProfile userProfile = new UserProfile(
        null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );
    companyService.createCompany(company);
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);
    userService.create(user, deviceId);
    User createdUser = userService.getUser(email);
    recommendProfileService.createRecommendProfile(createdUser.userId(), createdUser.userProfile().userProfileId(), RecommendType.DAILY_RECOMMEND);

    // when
    var recommendProfiles = recommendProfileService.getRecommendProfiles(createdUser.userId());
    RecommendProfile recommendProfile = recommendProfiles.get(0);
    recommendProfileService.rateRecommendProfile(recommendProfile.recommendProfileId(), 5);

    // then
    assertEquals(5, recommendProfileRepository.findById(recommendProfile.recommendProfileId()).get().getRating());
  }
}