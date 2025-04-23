package pl.kamil_dywan.allegro.generated;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "from",
    "to"
})
@Generated("jsonschema2pojo")
public class DateTimeRange {

    @JsonProperty("from")
    private LocalDateTime from;

    @JsonProperty("to")
    private LocalDateTime to;

}
