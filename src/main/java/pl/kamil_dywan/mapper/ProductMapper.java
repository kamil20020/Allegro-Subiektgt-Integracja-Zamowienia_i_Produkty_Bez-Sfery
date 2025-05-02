package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.Product;

public interface ProductMapper {

    static Product map(Offer allegroOffer){

        return Product.builder()
            .suppliersProductCode(allegroOffer.getId())
            .description(allegroOffer.getName())
            .build();
    }
}
