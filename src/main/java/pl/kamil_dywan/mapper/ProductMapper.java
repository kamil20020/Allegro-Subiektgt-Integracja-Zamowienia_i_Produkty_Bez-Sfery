package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.Product;

public class ProductMapper {

    private ProductMapper(){


    }

    public static Product map(Offer allegroOffer){

        String suppliersProductCode = null;

        if(allegroOffer.getExternal() != null){
            suppliersProductCode = allegroOffer.getExternal().getId();
        }

        return Product.builder()
            .suppliersProductCode(suppliersProductCode)
            .description(allegroOffer.getName())
            .build();
    }
}
