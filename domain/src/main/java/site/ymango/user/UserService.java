package site.ymango.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.user.entity.UserCompanyEntity;
import site.ymango.user.entity.UserEntity;
import site.ymango.user.model.User;
import site.ymango.user.repository.UserCompanyRepository;
import site.ymango.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserCompanyRepository userCompanyRepository;
  private final ObjectMapper objectMapper;

  public User getUser(String email) {
    return objectMapper.convertValue(userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다.")), User.class);
  }

  @Transactional
  public User create(User user) {
    if (userRepository.existsByEmail(user.email())) {
      throw new RuntimeException("이미 존재하는 이메일입니다.");
    }

    UserCompanyEntity userCompanyEntity = userCompanyRepository.findByDomain(user.userProfile().userCompany().domain())
        .orElseThrow(() -> new RuntimeException("존재하지 않는 회사입니다."));

    UserEntity userEntity = objectMapper.convertValue(user, UserEntity.class);

    userEntity.getUserProfile().setUserCompany(userCompanyEntity);

    return objectMapper.convertValue(userRepository.save(userEntity), User.class);
  }
}
