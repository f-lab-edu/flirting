package site.ymango.company.controller.v1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import site.ymango.auth.service.JwtService;
import site.ymango.company.CompanyService;
import site.ymango.user.model.Company;

@WebMvcTest(CompanyController.class)
@MockBean({JpaMetamodelMappingContext.class, JwtService.class})
class CompanyControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CompanyService companyService;

  @Test
  @WithMockUser
  @DisplayName("회사 정보 조회")
  void getCompanies() throws Exception {
    given(companyService.getCompanies(any(), any(), any())).willReturn(List.of(
        Company.builder()
            .companyId(1)
            .name("test")
            .domain("test")
            .iconUrl("test")
            .createdAt(LocalDateTime.now())
            .build()
    ));

    mockMvc.perform(get("/v1/companies"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].companyId").value(1))
        .andExpect(jsonPath("$.data[0].name").value("test"))
        .andExpect(jsonPath("$.data[0].domain").value("test"))
        .andExpect(jsonPath("$.data[0].iconUrl").value("test"))
        .andExpect(jsonPath("$.data[0].createdAt").exists());
  }
}