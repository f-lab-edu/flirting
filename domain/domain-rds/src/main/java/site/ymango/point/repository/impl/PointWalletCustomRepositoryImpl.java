package site.ymango.point.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.ymango.point.repository.PointWalletCustomRepository;

@Repository
@RequiredArgsConstructor
public class PointWalletCustomRepositoryImpl implements PointWalletCustomRepository {
  private final JPAQueryFactory queryFactory;


}
