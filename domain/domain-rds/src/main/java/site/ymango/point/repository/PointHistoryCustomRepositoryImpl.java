package site.ymango.point.repository;


import static site.ymango.point.entity.QPointHistoryEntity.pointHistoryEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.ymango.point.entity.PointHistoryEntity;
import site.ymango.point.enums.TransactionType;

@Repository
@RequiredArgsConstructor
public class PointHistoryCustomRepositoryImpl implements PointHistoryCustomRepository {

  private final JPAQueryFactory queryFactory;
}
