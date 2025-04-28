package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.Cost;
import pl.kamil_dywan.external.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Tax;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.own.Currency;
import pl.kamil_dywan.external.subiektgt.generated.TaxSubTotal;
import pl.kamil_dywan.external.subiektgt.own.TaxRateCodeMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AllegroLineItemMapper {

    private AllegroLineItemMapper(){


    }

    public static LineItem mapDeliveryToLineItem(Delivery allegroDelivery){

        BigDecimal taxRatePercentage = new BigDecimal("23.00");

        return LineItem.builder()
            .quantity(1)
            .originalPrice(new Cost(allegroDelivery.getCost().getAmount(), Currency.PLN))
            .price(new Cost(allegroDelivery.getCost().getAmount(), Currency.PLN))
            .offer(new Offer("Dostawa do klienta"))
            .boughtAt(allegroDelivery.getTime().getFrom())
            .tax(new Tax(taxRatePercentage, "", ""))
            .build();
    }
}
