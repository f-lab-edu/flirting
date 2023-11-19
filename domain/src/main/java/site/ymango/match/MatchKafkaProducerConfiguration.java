package site.ymango.match;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import site.ymango.match.model.MatchAcceptEvent;
import site.ymango.match.model.MatchRequestEvent;

@Configuration
public class MatchKafkaProducerConfiguration {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public Map<String, Object> producerConfig() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return config;
  }

  @Bean
  public ProducerFactory<String, MatchRequestEvent> matchRequestProducerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfig());
  }

  @Bean
  public KafkaTemplate<String, MatchRequestEvent> matchRequestKafkaTemplate() {
    return new KafkaTemplate<>(matchRequestProducerFactory());
  }

  @Bean
  public ProducerFactory<String, MatchAcceptEvent> matchAcceptProducerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfig());
  }

  @Bean
  public KafkaTemplate<String, MatchAcceptEvent> matchAcceptKafkaTemplate() {
    return new KafkaTemplate<>(matchAcceptProducerFactory());
  }
}
