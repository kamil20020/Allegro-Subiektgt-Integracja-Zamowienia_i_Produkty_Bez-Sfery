package pl.kamil_dywan.external.subiektgt.own.product;

import java.util.List;

public record ProductRelatedData(

    List<Product> products,
    List<ProductDetailedPrice> productPriceMappings
){}
