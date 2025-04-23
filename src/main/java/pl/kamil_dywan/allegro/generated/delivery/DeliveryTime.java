package pl.kamil_dywan.allegro.generated.delivery;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pl.kamil_dywan.allegro.generated.DateTimeRange;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "from",
    "to",
    "guaranteed",
    "dispatch"
})
@Generated("jsonschema2pojo")
public class DeliveryTime {

    @JsonProperty("from")
    private LocalDateTime from;

    @JsonProperty("to")
    private LocalDateTime to;

    @JsonProperty("guaranteed")
    private DateTimeRange guaranteed;

    @JsonProperty("dispatch")
    private DateTimeRange dispatch;

}
