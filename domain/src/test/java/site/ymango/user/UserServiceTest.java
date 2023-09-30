package site.ymango.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.enums.UserStatus;
import site.ymango.user.model.Location;
import site.ymango.user.model.User;
import site.ymango.user.model.UserCompany;
import site.ymango.user.model.UserProfile;

@SpringBootTest
class UserServiceTest {

  @Autowired
  private UserService userService;

  @Test
  @Transactional
  @DisplayName("회원가입")
  void signup() {
    String email = "test@test.com";
    String password = "1234";
    Gender gender = Gender.MALE;
    String username = "username";
    LocalDate birthdate = LocalDate.now();
    String sido = "서울";
    String sigungu = "강남구";
    Mbti mbti = Mbti.INFJ;
    String preferMbti = "EOFP";
    Location location = new Location(1.0, 2.0);

    UserCompany company = new UserCompany(
        null, null, "gmail.com", null, null, null, null
    );
    UserProfile userProfile = new UserProfile(
        null, username, gender, birthdate, sido, sigungu, mbti, preferMbti, location, company, null, null, null
    );
    User user = new User(
        null, email, password, UserStatus.ACTIVE, null, null, null, userProfile
    );

    User newUser = userService.create(user);
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

}