package pl.kamil_dywan.external.allegro.generated.offer_product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamil_dywan.external.allegro.generated.order_item.ExternalId;

import javax.annotation.processing.Generated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "external"
})
@Generated("jsonschema2pojo")
public class PatchProductOffer {

    @JsonProperty("external")
    private ExternalId externalId;

}
