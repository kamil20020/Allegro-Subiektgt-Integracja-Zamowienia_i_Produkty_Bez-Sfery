package pl.kamil_dywan.external.allegro.own.order;

import lombok.*;
import pl.kamil_dywan.external.subiektgt.own.product.TaxRateCodeMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderTaxSummary {

    private BigDecimal taxRateValue = BigDecimal.ZERO;
    private BigDecimal totalWithoutTax = BigDecimal.ZERO;
    private BigDecimal totalWithTax = BigDecimal.ZERO;
    private BigDecimal totalTaxValue = BigDecimal.ZERO;

    private OrderTaxSummary(BigDecimal taxRateValue){

        this.taxRateValue = taxRateValue;
    }

    public static OrderTaxSummary getEmpty(TaxRateCodeMapping taxRateCodeMapping){

        BigDecimal taxRateValue = BigDecimal.valueOf(taxRateCodeMapping.getValue());

        return new OrderTaxSummary(taxRateValue);
    }

    public void update(OrderItemMoneyStats orderItemMoneyStats){

        this.totalWithoutTax = this.totalWithoutTax.add(orderItemMoneyStats.getTotalPriceWithoutTax());
        this.totalWithTax = this.totalWithTax.add(orderItemMoneyStats.getTotalPriceWithTax());
        this.totalTaxValue = this.totalTaxValue.add(orderItemMoneyStats.getTotalTaxValue());
    }

    public void scale(int scale, RoundingMode roundingMode){

        taxRateValue = taxRateValue.setScale(scale, roundingMode);
        totalWithoutTax = totalWithoutTax.setScale(scale, roundingMode);
        totalWithTax = totalWithTax.setScale(scale, roundingMode);
        totalTaxValue = totalTaxValue.setScale(scale, roundingMode);
    }

    public static Map<TaxRateCodeMapping, OrderTaxSummary> getEmptyMappingsForAllTaxesRates(){

        Map<TaxRateCodeMapping, OrderTaxSummary> taxesMappings = new LinkedHashMap<>();

        taxesMappings.put(TaxRateCodeMapping.H, getEmpty(TaxRateCodeMapping.H));
        taxesMappings.put(TaxRateCodeMapping.L, getEmpty(TaxRateCodeMapping.L));
        taxesMappings.put(TaxRateCodeMapping.Z, getEmpty(TaxRateCodeMapping.Z));

        return taxesMappings;
    }

    public static Map<TaxRateCodeMapping, Integer> getTaxesRatesOccurs(){

        Map<TaxRateCodeMapping, Integer> taxRatesOccurs = new LinkedHashMap<>();

        taxRatesOccurs.put(TaxRateCodeMapping.H, 0);
        taxRatesOccurs.put(TaxRateCodeMapping.L, 0);
        taxRatesOccurs.put(TaxRateCodeMapping.Z, 0);

        return taxRatesOccurs;
    }

    public static int getNumberOfPresentTaxes(Map<TaxRateCodeMapping, OrderTaxSummary> taxesSubTotalsMappings){

        return (int) taxesSubTotalsMappings.values().stream()
            .map(taxSummary -> taxSummary.totalWithoutTax)
            .filter(totalWithoutTax -> totalWithoutTax.doubleValue() > 0d)
            .count();

    }

}
