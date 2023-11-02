package site.ymango.company;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import site.ymango.DatabaseClearExtension;
import site.ymango.company.entity.CompanyEntity;
import site.ymango.company.repository.CompanyRepository;
import site.ymango.user.model.Company;

@SpringBootTest
@ExtendWith(DatabaseClearExtension.class)
class CompanyServiceTest {
  @Autowired
  private CompanyService companyService;

  @Autowired
  private CompanyRepository companyRepository;

  @Test
  @DisplayName("회사 정보 조회 - 페이징")
  void getCompany() {
    int itemSize = 111;
    int pageSize = 10;
    IntStream.range(0, itemSize).mapToObj(i -> CompanyEntity.builder().domain("test" + i).iconUrl("test" + i).name("test" + i).build())
        .forEach(companyRepository::save);

    IntStream.range(0, itemSize / pageSize).reduce(0, (cursor, i) -> {
      List<Company> companies = companyService.getCompanies(cursor, "", Pageable.ofSize(pageSize));
      assertEquals(pageSize, companies.size());
      assertEquals(cursor, companies.get(0).companyId() - 1);
      assertEquals(cursor + pageSize, companies.get(pageSize - 1).companyId());
      return cursor + pageSize;
    });
  }
}