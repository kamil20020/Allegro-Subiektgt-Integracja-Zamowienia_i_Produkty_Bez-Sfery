package pl.kamil_dywan.mapper;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.invoice_item.ExternalId;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.Product;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void shouldMapWithExternalId() {

        //given
        Offer allegroOffer = Offer.builder()
            .external(new ExternalId("Id"))
            .name("Offer name")
            .build();

        //when
        Product product = ProductMapper.map(allegroOffer);

        //then
        assertNotNull(product);
        assertEquals(allegroOffer.getExternal().getId(), product.getSuppliersProductCode());
        assertEquals(allegroOffer.getName(), product.getDescription());
    }

    @Test
    public void shouldMapWithoutExternalId(){

        //given
        Offer allegroOffer = Offer.builder()
            .name("Offer name")
            .build();

        //when
        Product product = ProductMapper.map(allegroOffer);

        //then
        assertNotNull(product);
        assertEquals(allegroOffer.getName(), product.getDescription());
    }
}