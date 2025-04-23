package pl.kamil_dywan.allegro.generated.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import pl.kamil_dywan.allegro.generated.Cost;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
public class Surcharge {

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
