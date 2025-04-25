package pl.kamil_dywan.external.allegro.generated.order;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pl.kamil_dywan.external.allegro.own.FulFillmentStatus;

import javax.annotation.processing.Generated;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "shipmentSummary"
})
@Generated("jsonschema2pojo")
public class Fulfillment {

    @JsonProperty("status")
    private FulFillmentStatus status;

    @JsonProperty("shipmentSummary")
    private ShipmentSummary shipmentSummary;

}
