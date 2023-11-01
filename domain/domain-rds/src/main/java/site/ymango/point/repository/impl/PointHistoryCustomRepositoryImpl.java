package site.ymango.point.repository.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.ymango.point.repository.PointHistoryCustomRepository;

@Repository
@RequiredArgsConstructor
public class PointHistoryCustomRepositoryImpl implements PointHistoryCustomRepository {

  private final JPAQueryFactory queryFactory;
}
