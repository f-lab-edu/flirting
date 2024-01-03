package site.ymango.user;

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
import site.ymango.exception.BaseException;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.enums.PerferMbti;
import site.ymango.user.enums.UserStatus;
import site.ymango.user.model.Location;
import site.ymango.user.model.User;
import site.ymango.user.model.Company;
import site.ymango.user.model.UserProfile;

@SpringBootTest
@ExtendWith(DatabaseClearExtension.class)
class UserServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private EmailVerificationService emailVerificationService;

  @Autowired
  private CompanyService companyService;

  @Test
  @DisplayName("회원 조회 - 사용자를 찾을 수 없습니다.")
  void getUserFail() {
    String email = "nomail@no.no";
    BaseException baseException = assertThrows(BaseException.class, () -> userService.getUser(email));
    assertEquals("사용자를 찾을 수 없습니다.", baseException.getMessage());
  }

  @Test
  @DisplayName("회원가입 - 성공")
  void signup() {
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

    Company company = Company.builder()
            .name("구글")
            .domain("gmail.com")
            .build();

    UserProfile userProfile = UserProfile.builder()
        .username(username)
        .gender(gender)
        .birthdate(birthdate)
        .sido(sido)
        .sigungu(sigungu)
        .mbti(mbti)
        .preferMbti(preferMbti)
        .location(location)
        .userCompany(company)
        .build();

    User user = User.builder()
        .email(email)
        .password(password)
        .userProfile(userProfile)
        .build();

    companyService.createCompany(company);

    // when
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);
    User newUser = userService.create(user, deviceId);

    // then
    UserProfile newUserProfile = newUser.userProfile();

    assertEquals(email, newUser.email());
    assertEquals(password, newUser.password());
    assertEquals(username, newUserProfile.username());
    assertEquals(gender, userProfile.gender());
    assertEquals(birthdate, userProfile.birthdate());
    assertEquals(sido, userProfile.sido());
    assertEquals(sigungu, userProfile.sigungu());
    assertEquals(mbti, userProfile.mbti());
    assertEquals(preferMbti, userProfile.preferMbti());
    assertEquals(location, userProfile.location());
    assertEquals(company.name(), userProfile.userCompany().name());
    assertEquals(company.domain(), userProfile.userCompany().domain());
  }

  @Test
  @DisplayName("회원가입 - 이메일이 인증되지 않았습니다.")
  void signup2() {
    String email = "test@test.com";
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

    Company company = Company.builder()
        .name("구글")
        .domain("gmail.com")
        .build();

    UserProfile userProfile = UserProfile.builder()
        .username(username)
        .gender(gender)
        .birthdate(birthdate)
        .sido(sido)
        .sigungu(sigungu)
        .mbti(mbti)
        .preferMbti(preferMbti)
        .location(location)
        .userCompany(company)
        .build();

    User user = User.builder()
        .email(email)
        .password(password)
        .userProfile(userProfile)
        .build();

    BaseException baseException = assertThrows(BaseException.class, () -> userService.create(user, deviceId));
    assertEquals("이메일이 인증되지 않았습니다.", baseException.getMessage());
  }

  @Test
  @DisplayName("회원가입 - 이미 존재하는 사용자입니다.")
  void signup3() {
    // given
    String email = "test@gmail.com";
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

    Company company = Company.builder()
        .name("구글")
        .domain("gmail.com")
        .build();

    UserProfile userProfile = UserProfile.builder()
        .username(username)
        .gender(gender)
        .birthdate(birthdate)
        .sido(sido)
        .sigungu(sigungu)
        .mbti(mbti)
        .preferMbti(preferMbti)
        .location(location)
        .userCompany(company)
        .build();

    User user = User.builder()
        .email(email)
        .password(password)
        .userProfile(userProfile)
        .build();

    companyService.createCompany(company);
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);

    // when then
    userService.create(user, deviceId);
    BaseException baseException = assertThrows(BaseException.class, () -> userService.create(user, deviceId));
    assertEquals("이미 존재하는 사용자입니다.", baseException.getMessage());
  }
}