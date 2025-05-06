package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.Cost;
import pl.kamil_dywan.external.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Tax;
import pl.kamil_dywan.external.allegro.own.Currency;

import java.math.BigDecimal;

public interface AllegroLineItemMapper {

    static LineItem mapDeliveryToLineItem(Delivery allegroDelivery){

        BigDecimal taxRatePercentage = new BigDecimal("23.00");

        Offer deliveryOffer = Offer.builder()
            .id("DOSTAWA")
            .name("Dostawa do klienta")
            .build();

        return LineItem.builder()
            .quantity(1)
            .originalPrice(new Cost(allegroDelivery.getCost().getAmount(), Currency.PLN))
            .price(new Cost(allegroDelivery.getCost().getAmount(), Currency.PLN))
            .offer(deliveryOffer)
            .boughtAt(allegroDelivery.getTime().getFrom())
            .tax(new Tax(taxRatePercentage, "", ""))
            .build();
    }
}
