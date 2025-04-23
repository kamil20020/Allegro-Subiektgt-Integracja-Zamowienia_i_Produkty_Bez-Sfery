package pl.kamil_dywan.allegro.generated.order;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pl.kamil_dywan.allegro.generated.Payment;
import pl.kamil_dywan.allegro.generated.buyer.Buyer;
import pl.kamil_dywan.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.allegro.own.OrderStatus;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "messageToSeller",
    "buyer",
    "payment",
    "status",
    "fulfillment",
    "delivery",
    "invoice",
    "lineItems",
    "surcharges",
    "discounts",
    "note",
    "marketplace",
    "summary",
    "updatedAt",
    "revision"
})
@Generated("jsonschema2pojo")
public class Order {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("messageToSeller")
    private String messageToSeller;

    @JsonProperty("buyer")
    private Buyer buyer;

    @JsonProperty("payment")
    private Payment payment;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("fulfillment")
    private Fulfillment fulfillment;

    @JsonProperty("delivery")
    private Delivery delivery;

    @JsonProperty("invoice")
    private Invoice invoice;

    @JsonProperty("lineItems")
    private List<LineItem> lineItems = new ArrayList<>();

    @JsonProperty("surcharges")
    private List<Surcharge> surcharges = new ArrayList<>();

    @JsonProperty("discounts")
    private List<Discount> discounts = new ArrayList<>();

    @JsonProperty("note")
    private Note note;

    @JsonProperty("marketplace")
    private Marketplace marketplace;

    @JsonProperty("summary")
    private Summary summary;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("revision")
    private String revision;

}
