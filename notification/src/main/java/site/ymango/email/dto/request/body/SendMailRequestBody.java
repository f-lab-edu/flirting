package site.ymango.email.dto.request.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.ymango.email.model.Recipient;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMailRequestBody {
  @JsonProperty("senderName")
  private String senderName;
  @JsonProperty("templateSid")
  private String templateSid;
  @JsonProperty("recipients")
  private List<Recipient> recipients;
}
