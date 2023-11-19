package site.ymango.config;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import site.ymango.match.model.MatchAcceptEvent;
import site.ymango.match.model.MatchRequestEvent;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public ConsumerFactory<String, MatchAcceptEvent> matchAcceptEventConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        JsonDeserializer.TRUSTED_PACKAGES, "*"
    ), new StringDeserializer(), new JsonDeserializer<>(MatchAcceptEvent.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, MatchAcceptEvent> matchAcceptEventConcurrentKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, MatchAcceptEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(matchAcceptEventConsumerFactory());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, MatchRequestEvent> matchRequestEventConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        JsonDeserializer.TRUSTED_PACKAGES, "*"
    ), new StringDeserializer(), new JsonDeserializer<>(MatchRequestEvent.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, MatchRequestEvent> matchRequestEventConcurrentKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, MatchRequestEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(matchRequestEventConsumerFactory());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, Long> longConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class
    ));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Long> longKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Long> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(longConsumerFactory());
    return factory;
  }
}
