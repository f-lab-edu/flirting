package site.ymango.match;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.ymango.DatabaseClearExtension;
import site.ymango.company.CompanyService;
import site.ymango.email_verification.EmailVerificationService;
import site.ymango.match.entity.MatchRequestEntity;
import site.ymango.match.model.RequestMatch;
import site.ymango.match.repository.MatchRequestRepository;
import site.ymango.recommend_profile.entity.RecommendProfileEntity;
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
class MatchServiceTest {
  @Autowired
  private MatchService matchService;
  @Autowired
  private MatchRequestRepository matchRequestRepository;
  @Autowired
  private RecommendProfileRepository recommendProfileRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private CompanyService companyService;
  @Autowired
  private EmailVerificationService emailVerificationService;

  @BeforeEach
  void setUp() {
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

  }

  @Test
  @DisplayName("get matched users")
  void test01() {
    List<MatchRequestEntity> matchRequestEntities = List.of(MatchRequestEntity.builder()
            .userId(1L)
            .targetUserId(2L)
            .accepted(Boolean.TRUE)
            .expiredAt(LocalDateTime.now().plusDays(7))
            .build(),
        MatchRequestEntity.builder()
            .userId(2L)
            .targetUserId(1L)
            .accepted(Boolean.TRUE)
            .expiredAt(LocalDateTime.now().plusDays(7))
            .build()
    );

    matchRequestRepository.saveAll(matchRequestEntities);

    // when
    List<RequestMatch> matchedUsers = matchService.getMatchedUsers(1L);

    // then
    assertEquals(1, matchedUsers.size());
  }

  @Test
  @DisplayName("create match request")
  void createMatchRequest() {
    // given
    String email = "test2@test.com";
    String targetUserEmail = "test3@test.com";

    User user = userService.getUser(email);
    User targetUser = userService.getUser(targetUserEmail);

    RecommendProfileEntity recommendProfile = RecommendProfileEntity.builder()
        .userId(user.userId())
        .userProfileId(targetUser.userProfile().userProfileId())
        .expiredAt(LocalDateTime.now().plusDays(7))
        .build();

    recommendProfileRepository.save(recommendProfile);

    // when
    matchService.createMatchRequest(user.userId(), recommendProfile.getRecommendProfileId());

    // then
    List<MatchRequestEntity> matchRequestEntities = matchRequestRepository.findAll();
    assertEquals(1, matchRequestEntities.size());
  }


  @Test
  @DisplayName("accept match request")
  void acceptMatchRequest() {
    // given
    String email = "test2@test.com";
    String targetUserEmail = "test3@test.com";

    User user = userService.getUser(email);
    User targetUser = userService.getUser(targetUserEmail);

    RecommendProfileEntity recommendProfile = RecommendProfileEntity.builder()
        .userId(user.userId())
        .userProfileId(targetUser.userProfile().userProfileId())
        .expiredAt(LocalDateTime.now().plusDays(7))
        .build();
    recommendProfileRepository.save(recommendProfile);
    matchService.createMatchRequest(user.userId(), recommendProfile.getRecommendProfileId());
    List<MatchRequestEntity> matchRequestEntities = matchRequestRepository.findAll();

    // when
    matchService.acceptMatchRequest(targetUser.userId(), matchRequestEntities.get(0).getMatchRequestId(), user.userId());

    // then
    List<MatchRequestEntity> acceptedMatchRequestEntities = matchRequestRepository.findAll();
    assertEquals(1, acceptedMatchRequestEntities.size());
    assertTrue(acceptedMatchRequestEntities.get(0).getAccepted());
  }

  @Test
  @DisplayName("remove match request")
  void removeMatchRequest() {
    // given
    long userId = 1L;
    long userProfileId = 2L;
    RecommendProfileEntity recommendProfile = RecommendProfileEntity.builder()
        .userProfileId(userProfileId)
        .userId(userId)
        .expiredAt(LocalDateTime.now().plusDays(7))
        .build();

    recommendProfileRepository.save(recommendProfile);

    matchService.createMatchRequest(userId, recommendProfile.getRecommendProfileId());
    List<MatchRequestEntity> matchRequestEntities = matchRequestRepository.findAll();
    assertEquals(1, matchRequestEntities.size());

    // when
    matchService.removeMatchRequest(userId, matchRequestEntities.get(0).getMatchRequestId());

    // then
    List<MatchRequestEntity> removedMatchRequestEntities = matchRequestRepository.findAll();
    assertEquals(0, removedMatchRequestEntities.size());
  }

  @Test
  @DisplayName("get request matches")
  void getRequestMatches() {
    // given
    long userId = 1L;
    long userProfileId = 2L;
    RecommendProfileEntity recommendProfile = RecommendProfileEntity.builder()
        .userProfileId(userProfileId)
        .userId(userId)
        .expiredAt(LocalDateTime.now().plusDays(7))
        .build();

    recommendProfileRepository.save(recommendProfile);

    matchService.createMatchRequest(userId, recommendProfile.getRecommendProfileId());
    List<MatchRequestEntity> matchRequestEntities = matchRequestRepository.findAll();
    assertEquals(1, matchRequestEntities.size());

    // when
    List<RequestMatch> requestMatches = matchService.getRequestMatches(userId);

    // then
    assertEquals(1, requestMatches.size());
  }
}