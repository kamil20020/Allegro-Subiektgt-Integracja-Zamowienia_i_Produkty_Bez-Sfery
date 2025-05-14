package pl.kamil_dywan.external.allegro.own.order;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderTotalMoneyStats {

    private Integer numberOfOrderItems = 0;
    private Integer numberOfTaxes = 0;
    private BigDecimal totalWithoutTax = BigDecimal.ZERO;
    private BigDecimal totalWithTax = BigDecimal.ZERO;
    private BigDecimal taxValue = BigDecimal.ZERO;

    public void setNumberOfOrderItems(Integer numberOfOrderItems) {

        this.numberOfOrderItems = numberOfOrderItems;
    }

    public void setNumberOfTaxes(Integer numberOfTaxes) {

        this.numberOfTaxes = numberOfTaxes;
    }

    public void update(BigDecimal totalPriceWithoutTax, BigDecimal taxValue, BigDecimal totalWithTax){

        this.totalWithoutTax = this.totalWithoutTax.add(totalPriceWithoutTax);
        this.taxValue = this.taxValue.add(taxValue);
        this.totalWithTax = this.totalWithTax.add(totalWithTax);
    }

    public void scale(int scale, RoundingMode roundingMode){

        totalWithoutTax = totalWithoutTax.setScale(scale, roundingMode);
        taxValue = taxValue.setScale(scale, roundingMode);
        totalWithTax = totalWithTax.setScale(scale, roundingMode);
    }
}