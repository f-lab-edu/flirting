package site.ymango.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.ymango.exception.BaseException;
import site.ymango.user.entity.EmailVerificationEntity;
import site.ymango.user.entity.UserEntity;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.enums.PerferMbti;
import site.ymango.user.enums.UserStatus;
import site.ymango.user.model.Location;
import site.ymango.user.model.User;
import site.ymango.user.model.Company;
import site.ymango.user.model.UserProfile;
import site.ymango.user.repository.EmailVerificationRepository;
import site.ymango.user.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EmailVerificationRepository emailVerificationRepository;

  @Test
  @DisplayName("회원 조회 - 사용자를 찾을 수 없습니다.")
  void getUserFail() {
    String email = "nomail@no.no";
    BaseException baseException = assertThrows(BaseException.class, () -> userService.getUser(email));
    assertEquals("사용자를 찾을 수 없습니다.", baseException.getMessage());
  }

  @Test
  @DisplayName("이메일 인증 요청 생성")
  void createEmailVerification() {
    String email = "test@test.io";
    String deviceId = "deviceId";
    String verificationNumber = "1234";

    userService.createEmailVerification(email, deviceId, verificationNumber);

    EmailVerificationEntity emailVerification = emailVerificationRepository.findByEmailAndDeviceIdAndVerificationNumberAndVerified(
        email, deviceId, verificationNumber, false).orElseThrow(() -> new IllegalArgumentException("이메일 인증 요청을 찾을 수 없습니다."));

    assertEquals(email, emailVerification.getEmail());
    assertEquals(deviceId, emailVerification.getDeviceId());
    assertEquals(verificationNumber, emailVerification.getVerificationNumber());

    emailVerificationRepository.delete(emailVerification);
  }

  @Test
  @DisplayName("이메일 검증 - 성공")
  void verifyEmail1() {
    String email = "test@test.com";
    String deviceId = "test_device_id";
    String verificationNumber = "1234";

    userService.createEmailVerification(email, deviceId, verificationNumber);
    userService.verifyEmail(email, deviceId, verificationNumber);

    EmailVerificationEntity emailVerification = emailVerificationRepository.findByEmailAndDeviceIdAndVerificationNumberAndVerified(
        email, deviceId, verificationNumber, true).orElseThrow(() -> new IllegalArgumentException("이메일 인증 요청을 찾을 수 없습니다."));

    assertTrue(emailVerification.isVerified());
    emailVerificationRepository.delete(emailVerification);
  }

  @Test
  @DisplayName("이메일 검증 - 이메일 인증 정보가 유효하지 않습니다.")
  void verifyEmail2() {
    String email = "test2@test.com";
    String deviceId = "test_device_id";
    String verificationNumber = "1234";

    BaseException baseException = assertThrows(BaseException.class, () -> userService.verifyEmail(email, deviceId, verificationNumber));
    assertEquals("이메일 인증 정보가 유효하지 않습니다.", baseException.getMessage());
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

    Company company = new Company(
        null, null, "gmail.com", null, null, null, null
    );
    UserProfile userProfile = new UserProfile(
        null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );

    // when
    userService.createEmailVerification(email, deviceId, verificationNumber);
    userService.verifyEmail(email, deviceId, verificationNumber);
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


    // tear down
    UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    EmailVerificationEntity emailVerification = emailVerificationRepository.findByEmailAndDeviceIdAndVerificationNumberAndVerified(
        email, deviceId, verificationNumber, true).orElseThrow(() -> new IllegalArgumentException("이메일 인증 요청을 찾을 수 없습니다."));
    userRepository.delete(userEntity);
    emailVerificationRepository.delete(emailVerification);
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

    Company company = new Company(
        null, null, "gmail.com", null, null, null, null
    );
    UserProfile userProfile = new UserProfile(
        null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );

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

    Company company = new Company(
        null, null, "gmail.com", null, null, null, null
    );
    UserProfile userProfile = new UserProfile(
        null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );

    userService.createEmailVerification(email, deviceId, verificationNumber);
    userService.verifyEmail(email, deviceId, verificationNumber);

    // when then
    BaseException baseException = assertThrows(BaseException.class, () -> userService.create(user, deviceId));
    assertEquals("이미 존재하는 사용자입니다.", baseException.getMessage());

    // tear down
    EmailVerificationEntity emailVerification = emailVerificationRepository.findByEmailAndDeviceIdAndVerificationNumberAndVerified(
        email, deviceId, verificationNumber, true).orElseThrow(() -> new IllegalArgumentException("이메일 인증 요청을 찾을 수 없습니다."));
    emailVerificationRepository.delete(emailVerification);
  }
}