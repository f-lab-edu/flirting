package site.ymango.recommend_profile;

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
import site.ymango.point.PointService;
import site.ymango.point.enums.EventType;
import site.ymango.recommend_profile.model.RecommendProfile;
import site.ymango.recommend_profile.repository.RecommendProfileRepository;
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
  private PointService pointService;

  @Autowired
  private EmailVerificationService emailVerificationService;

  @Autowired
  private RecommendProfileRepository recommendProfileRepository;

  @Test
  @DisplayName("추천 프로필 생성 - 무료")
  void test01() {
    // given
    String email = "test2@test.com";
    String targetUserEmail = "test3@test.com";
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
        null, null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    UserProfile targetUserProfile = new UserProfile(
        null, null, username + 'a', Gender.FEMALE, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );
    User targetUser = new User(
        null, targetUserEmail, password, UserStatus.ACTIVE, null, null, null, targetUserProfile
    );
    companyService.createCompany(company);
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.createEmailVerification(targetUserEmail, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(targetUserEmail, deviceId, verificationNumber);
    userService.create(user, deviceId);
    userService.create(targetUser, deviceId);
    User createdUser = userService.getUser(email);

    // when
    recommendProfileService.createRecommendProfile(createdUser.userId());

    // then
    assertEquals(1, recommendProfileRepository.count());
  }

  @Test
  @DisplayName("추천 프로필 생성 - 유료")
  void test05() {
    // given
    String email = "test2@test.com";
    String targetUserEmail = "test3@test.com";
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
        null, null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    UserProfile targetUserProfile = new UserProfile(
        null, null, username + 'a', Gender.FEMALE, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );
    User targetUser = new User(
        null, targetUserEmail, password, UserStatus.ACTIVE, null, null, null, targetUserProfile
    );
    companyService.createCompany(company);
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.createEmailVerification(targetUserEmail, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(targetUserEmail, deviceId, verificationNumber);
    User user1 = userService.create(user, deviceId);
    userService.create(targetUser, deviceId);
    pointService.createPointWallet(user1.userId());
    pointService.addPoint(user1.userId(), 1000, EventType.PURCHASE);
    User createdUser = userService.getUser(email);

    // when
    recommendProfileService.createRecommendProfileByPoint(createdUser.userId());

    // then
    assertEquals(1, recommendProfileRepository.count());

  }

  @Test
  @DisplayName("추천 프로필 조회 - 성공")
  void test02() {
    // given
    String email = "test2@test.com";
    String targetUserEmail = "test3@test.com";
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
        null, null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    UserProfile targetUserProfile = new UserProfile(
        null, null,username + 'a', Gender.FEMALE, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );
    User targetUser = new User(
        null, targetUserEmail, password, UserStatus.ACTIVE, null, null, null, targetUserProfile
    );
    companyService.createCompany(company);
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.createEmailVerification(targetUserEmail, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(targetUserEmail, deviceId, verificationNumber);
    userService.create(user, deviceId);
    userService.create(targetUser, deviceId);
    User createdUser = userService.getUser(email);
    recommendProfileService.createRecommendProfile(createdUser.userId());

    // then
    var recommendProfiles = recommendProfileService.getRecommendProfiles(createdUser.userId());
    assertEquals(1, recommendProfiles.size());
  }

  @Test
  @DisplayName("추천 프로필 삭제")
  void test03() {
    // given
    String email = "test2@test.com";
    String targetUserEmail = "test3@test.com";
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
        null, null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    UserProfile targetUserProfile = new UserProfile(
        null, null, username + 'a', Gender.FEMALE, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );
    User targetUser = new User(
        null, targetUserEmail, password, UserStatus.ACTIVE, null, null, null, targetUserProfile
    );
    companyService.createCompany(company);
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.createEmailVerification(targetUserEmail, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(targetUserEmail, deviceId, verificationNumber);
    userService.create(user, deviceId);
    userService.create(targetUser, deviceId);
    User createdUser = userService.getUser(email);
    recommendProfileService.createRecommendProfile(createdUser.userId());

    // when
    var recommendProfiles = recommendProfileService.getRecommendProfiles(createdUser.userId());
    RecommendProfile recommendProfile = recommendProfiles.get(0);
    recommendProfileService.deleteRecommendProfile(createdUser.userId(), recommendProfile.recommendProfileId());

    // then
    assertEquals(0, recommendProfileRepository.count());
  }

  @Test
  @DisplayName("추천 프로필 평가")
  void test04() {
    // given
    String email = "test2@test.com";
    String targetUserEmail = "test3@test.com";
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
        null, null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    UserProfile targetUserProfile = new UserProfile(
        null, null, username + 'a', Gender.FEMALE, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );
    User targetUser = new User(
        null, targetUserEmail, password, UserStatus.ACTIVE, null, null, null, targetUserProfile
    );
    companyService.createCompany(company);
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.createEmailVerification(targetUserEmail, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(targetUserEmail, deviceId, verificationNumber);
    userService.create(user, deviceId);
    userService.create(targetUser, deviceId);
    User createdUser = userService.getUser(email);
    recommendProfileService.createRecommendProfile(createdUser.userId());

    // when
    var recommendProfiles = recommendProfileService.getRecommendProfiles(createdUser.userId());
    RecommendProfile recommendProfile = recommendProfiles.get(0);
    recommendProfileService.rateRecommendProfile(createdUser.userId(), recommendProfile.recommendProfileId(), 5);

    // then
    assertEquals(5, recommendProfileRepository.findById(recommendProfile.recommendProfileId()).get().getRating());
  }
}