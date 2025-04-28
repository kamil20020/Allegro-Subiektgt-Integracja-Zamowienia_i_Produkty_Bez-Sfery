package pl.kamil_dywan.external.subiektgt.own.product;

import java.math.BigDecimal;

public record Product(

    String id,
    Integer type,
    String name,
    UOMCode uomCode,
    TaxRateCodeMapping taxRateCodeMapping,
    BigDecimal unitPriceWithoutPrice
){}
