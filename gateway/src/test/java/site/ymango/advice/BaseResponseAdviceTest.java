package site.ymango.advice;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
class BaseResponseAdviceTest {
  MockMvc mockMvc;

  @JsonSerialize
  public static class EmptyObject {}

  @RestController
  @RequestMapping("/tests")
  public static class TestController {
    @GetMapping("/{status}/object")
    public ResponseEntity<Object> object(@PathVariable int status) {
      return new ResponseEntity<>(new Object(), HttpStatus.valueOf(status));
    }

    @GetMapping("/{status}/string")
    public ResponseEntity<String> string(@PathVariable int status) {
      return new ResponseEntity<>("hello world", HttpStatus.valueOf(status));
    }
    @GetMapping("/{status}/int")
    public ResponseEntity<Integer> integer(@PathVariable int status) {
      return new ResponseEntity<>(123456789, HttpStatus.valueOf(status));
    }

    @GetMapping("/{status}/empty-object")
    public ResponseEntity<Object> emptyObject(@PathVariable int status) {
      return new ResponseEntity<>(new EmptyObject(), HttpStatus.valueOf(status));
    }
  }

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
        .setControllerAdvice(new BaseResponseAdvice())
        .build();
  }

  @Test
  void testBypassObject() throws Exception {
    mockMvc.perform(get("/tests/200/object").accept(MediaType.APPLICATION_JSON))
        .andExpect(content().string(""))
        .andReturn();

    mockMvc.perform(get("/tests/500/object").accept(MediaType.APPLICATION_JSON))
        .andExpect(content().string(""))
        .andReturn();
  }

  @Test
  void testBypassString() throws Exception {
    // class org.springframework.http.converter.StringHttpMessageConverter
    mockMvc.perform(get("/tests/200/string"))
        .andExpect(content().string("hello world"));

    mockMvc.perform(get("/tests/500/string"))
        .andExpect(content().string("hello world"));
  }

  @Test
  void testBaseResponseInt() throws Exception {
    mockMvc.perform(get("/tests/200/int"))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.data").value(123456789))
        .andReturn();
  }

  @Test
  void testBaseResponseEmptyObject() throws Exception {
    mockMvc.perform(get("/tests/200/empty-object").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.data").isMap())
        .andReturn();
  }
}