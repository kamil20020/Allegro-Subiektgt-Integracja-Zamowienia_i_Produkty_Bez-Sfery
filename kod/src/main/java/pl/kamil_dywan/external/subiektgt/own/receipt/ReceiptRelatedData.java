package pl.kamil_dywan.external.subiektgt.own.receipt;

import pl.kamil_dywan.external.subiektgt.own.product.ProductRelatedData;

import java.util.List;

public record ReceiptRelatedData(

    Receipt receipt,
    List<ProductRelatedData> productRelatedData
){}
