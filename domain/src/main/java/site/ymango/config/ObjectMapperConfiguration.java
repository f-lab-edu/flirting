package site.ymango.config;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper()
        .setDateFormat(new StdDateFormat().withColonInTimeZone(true))
        .setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP))
        .registerModule(new JavaTimeModule());
  }

}
