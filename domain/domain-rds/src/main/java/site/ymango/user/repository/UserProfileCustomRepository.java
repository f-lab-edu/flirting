package site.ymango.user.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import site.ymango.user.entity.UserProfileEntity;
import site.ymango.user.enums.Gender;
import site.ymango.user.enums.Mbti;
import site.ymango.user.model.Location;

public interface UserProfileCustomRepository {
  Optional<UserProfileEntity> findRecommendProfile(Long userId, Location location, Gender gender, List<Mbti> preferredMbti, LocalDate birthdate);
}
