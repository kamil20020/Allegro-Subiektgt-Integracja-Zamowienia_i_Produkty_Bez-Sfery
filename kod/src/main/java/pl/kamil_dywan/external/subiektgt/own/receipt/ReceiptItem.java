package pl.kamil_dywan.external.subiektgt.own.receipt;

import pl.kamil_dywan.external.subiektgt.own.product.ProductType;
import pl.kamil_dywan.file.EppSerializable;

public class ReceiptItem extends EppSerializable {

    private ProductType productType;

    public ReceiptItem(String[] args) {

        super(args);

        this.productType = ProductType.valueOf(args[0]);
    }

    public ReceiptItem(ProductType productType) {

        super(null);

        this.productType = productType;
    }

    public ReceiptItem() {

        super(null);
    }
}
