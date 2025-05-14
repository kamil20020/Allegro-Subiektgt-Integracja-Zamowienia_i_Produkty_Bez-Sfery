
package pl.kamil_dywan.external.allegro.generated.order_item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.*;
import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import pl.kamil_dywan.external.allegro.generated.Cost;
import pl.kamil_dywan.external.allegro.own.order.OrderItemMoneyStats;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
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
public class OrderItem {

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
    private OrderItemReconciliation reconciliation;

    @JsonProperty("selectedAdditionalServices")
    private List<OrderItemAdditionalService> selectedAdditionalServices = new ArrayList<>();

    @JsonProperty("vouchers")
    private List<Voucher> vouchers = new ArrayList<>();

    @JsonProperty("tax")
    private Tax tax;

    @JsonProperty("boughtAt")
    private OffsetDateTime boughtAt;

    public OrderItemMoneyStats getMoneySummary(){

        BigDecimal quantityValue = BigDecimal.valueOf(quantity);

        BigDecimal taxRatePercentage = getTaxRatePercentage();
        BigDecimal taxRateValue = taxRatePercentage.multiply(
            BigDecimal.valueOf(0.01)
        );

        BigDecimal unitPriceWithTax = price.getAmount();
        BigDecimal unitPriceWithoutTax = unitPriceWithTax.divide(
            BigDecimal.ONE.add(taxRateValue),
            RoundingMode.HALF_UP
        );

        BigDecimal totalPriceWithTax = unitPriceWithTax.multiply(quantityValue);
        BigDecimal totalPriceWithoutTax = unitPriceWithoutTax.multiply(quantityValue);
        BigDecimal totalTaxValue = totalPriceWithTax.subtract(totalPriceWithoutTax);

        return new OrderItemMoneyStats(
            taxRatePercentage,
            unitPriceWithTax,
            unitPriceWithoutTax,
            totalPriceWithTax,
            totalPriceWithoutTax,
            totalTaxValue
        );
    }

    private BigDecimal getTaxRatePercentage(){

        if(tax == null){

            return BigDecimal.valueOf(23);
        }

        return tax.getRate();
    }



}
