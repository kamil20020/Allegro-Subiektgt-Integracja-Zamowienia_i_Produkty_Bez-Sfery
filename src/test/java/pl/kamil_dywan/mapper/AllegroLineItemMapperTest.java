package pl.kamil_dywan.mapper;

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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AllegroLineItemMapperTest {

    @ParameterizedTest
    @CsvSource(value = {
        "23, 23, 8, 0, 23",
        "8, 23, 8, 0, 8",
        "0, 23, 8, 0, 0"
    })
    void shouldMapDeliveryToLineItem(BigDecimal taxValue1, BigDecimal taxValue2, BigDecimal taxValue3, BigDecimal taxValue4, BigDecimal expectedTaxRatePercentage) {

        //given
        Delivery allegroDelivery = Delivery.builder()
            .cost(new Cost(new BigDecimal("34.56"), Currency.PLN))
            .time(new DeliveryTime(OffsetDateTime.now(), null, null, null))
            .build();

        LineItem lineItem1 = LineItem.builder()
            .tax(new Tax(taxValue1, "", ""))
            .build();

        LineItem lineItem2 = LineItem.builder()
            .tax(new Tax(taxValue2, "", ""))
            .build();

        LineItem lineItem3 = LineItem.builder()
            .tax(new Tax(taxValue3, "", ""))
            .build();

        LineItem lineItem4 = LineItem.builder()
            .tax(new Tax(taxValue4, "", ""))
            .build();

        List<LineItem> allegroLineItems = List.of(lineItem1, lineItem2, lineItem3, lineItem4);

        Order allegroOrder = Order.builder()
            .delivery(allegroDelivery)
            .lineItems(allegroLineItems)
            .build();

        //when
        LineItem gotDeliveryLineItem = AllegroLineItemMapper.mapDeliveryToLineItem(allegroOrder);

        //then
        assertNotNull(gotDeliveryLineItem);
        assertEquals(1, gotDeliveryLineItem.getQuantity());
        assertEquals(allegroDelivery.getCost().getAmount(), gotDeliveryLineItem.getPrice().getAmount());
        assertEquals(allegroDelivery.getCost().getAmount(), gotDeliveryLineItem.getOriginalPrice().getAmount());
        assertEquals(allegroDelivery.getTime().getFrom(), gotDeliveryLineItem.getBoughtAt());
        assertEquals(expectedTaxRatePercentage, gotDeliveryLineItem.getTax().getRate());
    }

    @Test
    public void shouldGet23TaxForLineItemsWithoutTaxesInfos(){

        //given
        Delivery allegroDelivery = Delivery.builder()
            .cost(new Cost(new BigDecimal("34.56"), Currency.PLN))
            .time(new DeliveryTime(OffsetDateTime.now(), null, null, null))
            .build();

        LineItem lineItem1 = LineItem.builder()
            .build();

        LineItem lineItem2 = LineItem.builder()
            .build();

        List<LineItem> allegroLineItems = List.of(lineItem1, lineItem2);

        Order allegroOrder = Order.builder()
            .delivery(allegroDelivery)
            .lineItems(allegroLineItems)
            .build();

        //when
        LineItem gotDeliveryLineItem = AllegroLineItemMapper.mapDeliveryToLineItem(allegroOrder);

        //then
        assertNotNull(gotDeliveryLineItem);
        assertEquals(1, gotDeliveryLineItem.getQuantity());
        assertEquals(allegroDelivery.getCost().getAmount(), gotDeliveryLineItem.getOriginalPrice().getAmount());
        assertEquals(allegroDelivery.getCost().getAmount(), gotDeliveryLineItem.getPrice().getAmount());
        assertEquals(allegroDelivery.getTime().getFrom(), gotDeliveryLineItem.getBoughtAt());
        assertEquals(new BigDecimal("23"), gotDeliveryLineItem.getTax().getRate());
    }
}