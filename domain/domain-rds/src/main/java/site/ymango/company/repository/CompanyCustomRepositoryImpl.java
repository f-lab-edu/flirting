package site.ymango.company.repository;

import static site.ymango.company.entity.QCompanyEntity.companyEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import site.ymango.company.entity.CompanyEntity;

@Repository
@RequiredArgsConstructor
public class CompanyCustomRepositoryImpl implements CompanyCustomRepository {
  private final JPAQueryFactory queryFactory;

  public Optional<CompanyEntity> findByDomain(String domain) {
    return Optional.ofNullable(queryFactory.selectFrom(companyEntity)
        .where(companyEntity.domain.eq(domain))
        .fetchOne());
  }

  public Optional<CompanyEntity> findByCompanyId(Integer companyId) {
    return Optional.ofNullable(queryFactory.selectFrom(companyEntity)
        .where(companyEntity.companyId.eq(companyId))
        .fetchOne());
  }

  public Page<CompanyEntity> findByName(Integer cursorId, String keyword, Pageable pageable) {
    List<CompanyEntity> results = queryFactory.selectFrom(companyEntity)
        .where(cursorIdGt(cursorId).and(companyEntity.name.contains(keyword)))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(companyEntity.name.asc())
        .fetch();

    return new PageImpl<>(results, pageable, results.size());
  }

  private BooleanExpression cursorIdGt(Integer cursorId) {
    return cursorId == null ? null : companyEntity.companyId.gt(cursorId);
  }
}
