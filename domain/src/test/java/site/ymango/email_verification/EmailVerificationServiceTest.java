package site.ymango.email_verification;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.ymango.email_verification.entity.EmailVerificationEntity;
import site.ymango.email_verification.repository.EmailVerificationRepository;
import site.ymango.exception.BaseException;

@SpringBootTest
class EmailVerificationServiceTest {
  @Autowired
  private EmailVerificationService emailVerificationService;

  @Autowired
  private EmailVerificationRepository emailVerificationRepository;

  @BeforeEach
  void setUp() {
    emailVerificationRepository.deleteAll();
  }

  @Test
  @DisplayName("이메일 인증 요청 생성")
  void createEmailVerification() {
    // given
    String email = "test@test.io";
    String deviceId = "deviceId";
    String verificationNumber = "1234";

    // when
    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);

    // then
    EmailVerificationEntity emailVerification = emailVerificationRepository.findOne(
        email, deviceId, verificationNumber, false).orElseThrow(() -> new IllegalArgumentException("이메일 인증 요청을 찾을 수 없습니다."));

    assertEquals(email, emailVerification.getEmail());
    assertEquals(deviceId, emailVerification.getDeviceId());
    assertEquals(verificationNumber, emailVerification.getVerificationNumber());
  }

  @Test
  @DisplayName("이메일 검증 - 성공")
  void verifyEmail1() {
    // given
    String email = "test@test.com";
    String deviceId = "test_device_id";
    String verificationNumber = "1234";

    emailVerificationService.createEmailVerification(email, deviceId, verificationNumber);

    // when
    emailVerificationService.verifyEmail(email, deviceId, verificationNumber);


    // then
    EmailVerificationEntity emailVerification = emailVerificationRepository.findOne(
        email, deviceId, verificationNumber, true).orElseThrow(() -> new IllegalArgumentException("이메일 인증 요청을 찾을 수 없습니다."));
    assertTrue(emailVerification.isVerified());
  }

  @Test
  @DisplayName("이메일 검증 - 이메일 인증 정보가 유효하지 않습니다.")
  void verifyEmail2() {
    String email = "test2@test.com";
    String deviceId = "test_device_id";
    String verificationNumber = "1234";

    BaseException baseException = assertThrows(BaseException.class, () -> emailVerificationService.verifyEmail(email, deviceId, verificationNumber));
    assertEquals("이메일 인증 정보가 유효하지 않습니다.", baseException.getMessage());
  }
}