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

    private static final int ROUNDING_SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public String getClientName(){

        if(invoice.isRequired()){

            InvoiceAddress invoiceAddress = invoice.getAddress();

            if (invoiceAddress.getCompany() == null) {

                return invoiceAddress.getNaturalPerson().simpleToString();
            }

            return invoiceAddress.getCompany().getName();
        }

        if(buyer.getCompanyName() != null){

            return buyer.getCompanyName();
        }

        return buyer.getFirstName() + " " + buyer.getLastName();
    }

    public OrderMoneyStats getMoneySummary(){

        Map<TaxRateCodeMapping, OrderTaxSummary> taxesMappings = OrderTaxSummary.getEmptyMappingsForAllTaxesRates();

        List<OrderItemMoneyStats> orderItemsMoneyStats = new ArrayList<>();

        orderItems.stream()
            .forEach(orderItem -> {

                OrderItemMoneyStats orderItemMoneyStats = orderItem.getMoneySummary();
                orderItemsMoneyStats.add(orderItemMoneyStats);

                BigDecimal taxRatePercentage = orderItemMoneyStats.getTaxRatePercentage();

                updateTaxesMappings(taxesMappings, taxRatePercentage, orderItemMoneyStats);
                orderItemMoneyStats.scale(ROUNDING_SCALE, ROUNDING_MODE);
            });

        OrderTotalMoneyStats totalSummary = getTotalSummary(taxesMappings);

        scaleTaxesMappings(taxesMappings, ROUNDING_SCALE, ROUNDING_MODE);
        totalSummary.scale(ROUNDING_SCALE, ROUNDING_MODE);

        return new OrderMoneyStats(
            orderItemsMoneyStats,
            (List<OrderTaxSummary>) taxesMappings.values(),
            totalSummary
        );
    }

    private static void updateTaxesMappings(
        Map<TaxRateCodeMapping, OrderTaxSummary> taxesMappings,
        BigDecimal taxRatePercentage,
        OrderItemMoneyStats orderItemMoneyStats
    ){
        TaxRateCodeMapping taxRateCodeMapping = TaxRateCodeMapping.getByValue(taxRatePercentage);
        OrderTaxSummary actualTaxSummary = taxesMappings.get(taxRateCodeMapping);

        actualTaxSummary.update(orderItemMoneyStats);
    }

    private OrderTotalMoneyStats getTotalSummary(Map<TaxRateCodeMapping, OrderTaxSummary> taxesMappings){

        OrderTotalMoneyStats totalSummary = new OrderTotalMoneyStats();

        totalSummary.setNumberOfOrderItems(orderItems.size());
        totalSummary.setNumberOfTaxes(OrderTaxSummary.getNumberOfPresentTaxes(taxesMappings));

        taxesMappings.values()
            .forEach(taxSummary -> {

                totalSummary.update(
                    taxSummary.getTotalWithoutTax(),
                    taxSummary.getTotalTaxValue(),
                    taxSummary.getTotalWithTax()
                );
            });

        return totalSummary;
    }

    private static void scaleTaxesMappings(
        Map<TaxRateCodeMapping, OrderTaxSummary> taxesMappings,
        int scale,
        RoundingMode roundingMode
    ){
        taxesMappings.values()
            .forEach(taxSummary -> {

                taxSummary.scale(scale, roundingMode);
            });
    }

}
