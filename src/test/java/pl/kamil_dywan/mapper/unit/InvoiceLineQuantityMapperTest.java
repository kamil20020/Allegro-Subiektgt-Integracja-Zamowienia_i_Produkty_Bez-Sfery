package pl.kamil_dywan.mapper.unit;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Product;
import pl.kamil_dywan.external.allegro.generated.invoice_item.ProductSet;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLineQuantity;
import pl.kamil_dywan.external.subiektgt.own.UOMCode;
import pl.kamil_dywan.mapper.InvoiceLineQuantityMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceLineQuantityMapperTest {

    @Test
    void shouldMapWhenProductIsIsNotNUll() {

        //given
        ProductSet productsSet = new ProductSet();
        productsSet.setProducts(List.of(new Product(), new Product()));

        Offer allegroOffer = Offer.builder()
            .productSet(productsSet)
            .build();

        LineItem allegroLineItem = LineItem.builder()
            .quantity(22)
            .offer(allegroOffer)
            .build();

        //when
        InvoiceLineQuantity gotInvoiceLineQuantity = InvoiceLineQuantityMapper.map(allegroLineItem);

        //then
        assertNotNull(gotInvoiceLineQuantity);
        assertEquals(allegroLineItem.getQuantity(), gotInvoiceLineQuantity.getAmount());
        assertEquals(productsSet.getProducts().size(), gotInvoiceLineQuantity.getPacksize());
        assertEquals(UOMCode.UNIT, gotInvoiceLineQuantity.getUomCode());
    }

    @Test
    public void shouldMapWhenProductSetIsNull(){

        //given
        Offer allegroOffer = Offer.builder()
            .build();

        LineItem allegroLineItem = LineItem.builder()
            .quantity(22)
            .offer(allegroOffer)
            .build();

        //when
        InvoiceLineQuantity gotInvoiceLineQuality = InvoiceLineQuantityMapper.map(allegroLineItem);

        //then
        assertNotNull(gotInvoiceLineQuality);
        assertEquals(allegroLineItem.getQuantity(), gotInvoiceLineQuality.getAmount());
        assertEquals(1, gotInvoiceLineQuality.getPacksize());
        assertEquals(UOMCode.UNIT, gotInvoiceLineQuality.getUomCode());
    }
}