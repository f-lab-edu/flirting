package site.ymango.recommend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecommendProfileCustomRepositoryImpl implements RecommendProfileCustomRepository {
  private final JPAQueryFactory queryFactory;
}
