
package pl.kamil_dywan.allegro.generated.invoice_item;

import java.time.LocalDateTime;
import java.util.*;
import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import pl.kamil_dywan.allegro.generated.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "offer",
    "quantity",
    "originalPrice",
    "price",
    "reconciliation",
    "selectedAdditionalServices",
    "vouchers",
    "tax",
    "boughtAt"
})
@Generated("jsonschema2pojo")
public class LineItem {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("offer")
    private Offer offer;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("originalPrice")
    private Cost originalPrice;

    @JsonProperty("price")
    private Cost price;

    @JsonProperty("reconciliation")
    private LineItemReconciliation reconciliation;

    @JsonProperty("selectedAdditionalServices")
    private List<LineItemAdditionalService> selectedAdditionalServices = new ArrayList<>();

    @JsonProperty("vouchers")
    private List<Voucher> vouchers = new ArrayList<>();

    @JsonProperty("tax")
    private Tax tax;

    @JsonProperty("boughtAt")
    private LocalDateTime boughtAt;

}
