package site.ymango.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ymango.exception.ErrorCode;
import site.ymango.exception.BaseException;
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
        .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND)), User.class);
  }

  @Transactional
  public User create(User user) {
    if (userRepository.existsByEmail(user.email())) {
      throw new BaseException(ErrorCode.USER_ALREADY_EXISTS, user.email());
    }

    UserCompanyEntity userCompanyEntity = userCompanyRepository.findByDomain(user.userProfile().userCompany().domain())
        .orElseThrow(() -> new BaseException(ErrorCode.COMPANY_NOT_FOUND));

    UserEntity userEntity = objectMapper.convertValue(user, UserEntity.class);

    userEntity.getUserProfile().setUserCompany(userCompanyEntity);

    return objectMapper.convertValue(userRepository.save(userEntity), User.class);
  }
}
