package pl.kamil_dywan.mapper.invoice;

import pl.kamil_dywan.external.allegro.generated.order_item.Offer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.Product;

public interface ProductMapper {

    static Product map(Offer allegroOffer){

        return Product.builder()
            .suppliersProductCode(allegroOffer.getId())
            .description(allegroOffer.getName())
            .build();
    }
}
