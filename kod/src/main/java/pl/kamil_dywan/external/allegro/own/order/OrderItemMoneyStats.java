package pl.kamil_dywan.external.allegro.own.order;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderItemMoneyStats {

    private BigDecimal taxRatePercentage;
    private BigDecimal unitPriceWithTax;
    private BigDecimal unitPriceWithoutTax;
    private BigDecimal totalPriceWithTax;
    private BigDecimal totalPriceWithoutTax;
    private BigDecimal totalTaxValue;

    public void scale(int scale, RoundingMode roundingMode){

        unitPriceWithTax = unitPriceWithTax.setScale(scale, roundingMode);
        unitPriceWithoutTax = unitPriceWithoutTax.setScale(scale, roundingMode);
        totalPriceWithTax = totalPriceWithTax.setScale(scale, roundingMode);
        totalPriceWithoutTax = totalPriceWithoutTax.setScale(scale, roundingMode);
        totalTaxValue = totalTaxValue.setScale(scale, roundingMode);
    }
}

