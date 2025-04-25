package pl.kamil_dywan.external.subiektgt.own;

import java.math.BigDecimal;

public record LineItemMoneyStats(
    BigDecimal taxRatePercentage,
    BigDecimal unitPriceWithTax,
    BigDecimal unitPriceWithoutTax,
    BigDecimal totalPriceWithTax,
    BigDecimal totalPriceWithoutTax,
    BigDecimal totalTaxValue
){}
