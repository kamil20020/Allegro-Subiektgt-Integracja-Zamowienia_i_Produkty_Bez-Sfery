package pl.kamil_dywan.external.subiektgt.own.invoice;

import java.math.BigDecimal;

public record InvoiceLineMoneyStats(
    BigDecimal taxRatePercentage,
    BigDecimal unitPriceWithTax,
    BigDecimal unitPriceWithoutTax,
    BigDecimal totalPriceWithTax,
    BigDecimal totalPriceWithoutTax,
    BigDecimal totalTaxValue
){}
