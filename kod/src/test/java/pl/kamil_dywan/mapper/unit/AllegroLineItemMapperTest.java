package pl.kamil_dywan.mapper.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.kamil_dywan.external.allegro.generated.Cost;
import pl.kamil_dywan.external.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.external.allegro.generated.delivery.DeliveryTime;
import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Tax;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.own.Currency;
import pl.kamil_dywan.mapper.AllegroLineItemMapper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AllegroLineItemMapperTest {

    @Test
    void shouldMapDeliveryToLineItem() {

        //given
        Delivery allegroDelivery = Delivery.builder()
            .cost(new Cost(new BigDecimal("34.56"), Currency.PLN))
            .time(new DeliveryTime(OffsetDateTime.now(), null, null, null))
            .build();

        BigDecimal expectedTaxRatePercentage = new BigDecimal("23.00");

        //when
        LineItem gotDeliveryLineItem = AllegroLineItemMapper.mapDeliveryToLineItem(allegroDelivery);

        //then
        assertNotNull(gotDeliveryLineItem);
        assertEquals(1, gotDeliveryLineItem.getQuantity());
        assertEquals(allegroDelivery.getCost().getAmount(), gotDeliveryLineItem.getPrice().getAmount());
        assertEquals(allegroDelivery.getCost().getAmount(), gotDeliveryLineItem.getOriginalPrice().getAmount());
        assertEquals(allegroDelivery.getTime().getFrom(), gotDeliveryLineItem.getBoughtAt());
        assertEquals(expectedTaxRatePercentage, gotDeliveryLineItem.getTax().getRate());
    }
}