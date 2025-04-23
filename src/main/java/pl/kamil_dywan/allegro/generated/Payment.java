package pl.kamil_dywan.allegro.generated;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "provider",
    "finishedAt",
    "paidAmount",
    "reconciliation",
    "features"
})
@Generated("jsonschema2pojo")
public class Payment {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("finishedAt")
    private LocalDateTime finishedAt;

    @JsonProperty("paidAmount")
    private Cost paidAmount;

    @JsonProperty("reconciliation")
    private Cost reconciliation;

    @JsonProperty("features")
    private List<String> features = new ArrayList<>();

}
