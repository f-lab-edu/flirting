package site.ymango.email.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.ymango.email.enums.RecipientType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipient {
  @JsonProperty("address")
  private String address;
  @JsonProperty("name")
  private String name;
  @Builder.Default
  @JsonProperty("type")
  private RecipientType type = RecipientType.R;
  @Builder.Default
  @JsonProperty("parameters")
  private Map<String, Object> parameters = Map.of();
}
