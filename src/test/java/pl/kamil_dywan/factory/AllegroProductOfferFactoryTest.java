package pl.kamil_dywan.factory;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.offer_product.ProductOffer;
import pl.kamil_dywan.external.allegro.generated.offer_product.SellingMode;
import pl.kamil_dywan.external.allegro.generated.offer_product.TaxSettings;
import pl.kamil_dywan.external.allegro.own.Country;
import pl.kamil_dywan.external.allegro.own.Currency;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AllegroProductOfferFactoryTest {

    @Test
    void shouldCreateDeliveryProductOffer() {

        //given

        //when
        ProductOffer deliveryProductOffer = AllegroProductOfferFactory.createDeliveryProductOffer();

        //then
        assertNotNull(deliveryProductOffer);

        SellingMode sellingMode = deliveryProductOffer.getSellingMode();
        TaxSettings taxSettings = deliveryProductOffer.getTaxSettings();

        assertNotNull(sellingMode);
        assertNotNull(taxSettings);
        assertEquals(1L, deliveryProductOffer.getId());
        assertEquals("Dostawa", deliveryProductOffer.getName());

        assertEquals(BigDecimal.ZERO, sellingMode.getPrice().getAmount());
        assertEquals(Currency.PLN, sellingMode.getPrice().getCurrency());

        assertNotNull(taxSettings.getTaxesFoCountries());
        assertEquals(1, taxSettings.getTaxesFoCountries().size());
        assertEquals(BigDecimal.valueOf(23), taxSettings.getTaxesFoCountries().get(0).getTaxRate());
        assertEquals(Country.PL, taxSettings.getTaxesFoCountries().get(0).getCountry());
    }
}