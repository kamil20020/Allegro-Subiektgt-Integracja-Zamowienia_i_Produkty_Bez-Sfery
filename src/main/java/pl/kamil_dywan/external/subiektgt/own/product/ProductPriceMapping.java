package pl.kamil_dywan.external.subiektgt.own.product;

import java.math.BigDecimal;
import java.util.List;

public record ProductPriceMapping(

    String productId,
    String groupName,
    BigDecimal unitPriceWithoutTax,
    BigDecimal unitPriceWithTax,
    BigDecimal markup,
    BigDecimal margin,
    BigDecimal profit
){}
