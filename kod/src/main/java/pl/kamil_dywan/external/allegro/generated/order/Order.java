package pl.kamil_dywan.external.allegro.generated.order;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pl.kamil_dywan.external.allegro.generated.Payment;
import pl.kamil_dywan.external.allegro.generated.buyer.Buyer;
import pl.kamil_dywan.external.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.external.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.allegro.generated.order_item.OrderItem;
import pl.kamil_dywan.external.allegro.own.order.*;
import pl.kamil_dywan.external.subiektgt.own.product.TaxRateCodeMapping;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
    "id",
    "messageToSeller",
    "buyer",
    "payment",
    "status",
    "fulfillment",
    "delivery",
    "invoice",
    "orderItems",
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
    private List<OrderItem> orderItems = new ArrayList<>();

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
    private OffsetDateTime updatedAt;

    @JsonProperty("revision")
    private String revision;

    public boolean hasInvoice(){

        return invoice.isRequired();
    }

    public boolean isBuyerCompany(){

        if(hasInvoice()){

            return invoice.hasCompany();
        }

        return buyer.hasCompany();
    }

    public String getClientName(){

        if(hasInvoice()){

            return invoice.getClientName();
        }

        return buyer.getName();
    }

}
