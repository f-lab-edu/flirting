package site.ymango.point.repository;

import static site.ymango.point.entity.QPointWalletEntity.pointWalletEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointWalletCustomRepositoryImpl implements PointWalletCustomRepository {
  private final JPAQueryFactory queryFactory;


}
