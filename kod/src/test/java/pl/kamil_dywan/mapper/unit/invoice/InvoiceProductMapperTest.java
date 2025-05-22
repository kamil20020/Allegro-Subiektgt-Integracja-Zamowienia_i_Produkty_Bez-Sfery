package pl.kamil_dywan.mapper.unit.invoice;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.order_item.ExternalId;
import pl.kamil_dywan.external.allegro.generated.order_item.Offer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.Product;
import pl.kamil_dywan.mapper.invoice.InvoiceProductMapper;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceProductMapperTest {

    @Test
    void shouldMap() {

        //given
        Offer allegroOffer = Offer.builder()
            .id("123")
            .name("Offer name")
            .build();

        //when
        Product product = InvoiceProductMapper.map(allegroOffer);

        //then
        assertNotNull(product);
        assertEquals(allegroOffer.getId(), product.getSuppliersProductCode());
        assertEquals(allegroOffer.getName(), product.getDescription());
    }

}