package pl.kamil_dywan.external.allegro.generated.offer_product;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "id",
    "name",
    "sellingMode",
    "taxSettings",
    "createdAt"
})
@Generated("jsonschema2pojo")
public class ProductOffer {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sellingMode")
    private SellingMode sellingMode;

    @JsonProperty("taxSettings")
    private TaxSettings taxSettings;

    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;

    @JsonIgnore
    public BigDecimal getTaxRate(){

        if(getTaxSettings() != null){

            return getTaxSettings().getTaxesFoCountries().get(0).getTaxRate();
        }

        return BigDecimal.valueOf(23);
    }

    @JsonIgnore
    public BigDecimal getPriceWithTax(){

        return sellingMode.getPrice().getAmount();
    }

    @JsonIgnore
    public BigDecimal getPriceWithoutTax(){

        BigDecimal taxRate = getTaxRate();
        BigDecimal unitPriceWithTax = getPriceWithTax();

        BigDecimal taxRateValue = taxRate.multiply(new BigDecimal("0.01"));

        return unitPriceWithTax.divide(
            BigDecimal.ONE.add(taxRateValue),
            RoundingMode.HALF_UP
        );
    }
}
