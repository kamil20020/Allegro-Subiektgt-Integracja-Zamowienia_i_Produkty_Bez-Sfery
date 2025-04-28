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

    public static LineItem mapDeliveryToLineItem(Order order){

        Delivery allegroDelivery = order.getDelivery();
        List<LineItem> allegroLineItems = order.getLineItems();

        Map<TaxRateCodeMapping, Integer> taxRatesOccurs = TaxSubTotal.getTaxesRatesOccurs();

        allegroLineItems
            .forEach(allegroLineItem -> {

                BigDecimal taxRatePercentage = getTaxRatePercentage(allegroLineItem);
                TaxRateCodeMapping taxRateCodeMapping = TaxRateCodeMapping.getByValue(taxRatePercentage);

                Integer taxRateOccurs = taxRatesOccurs.get(taxRateCodeMapping);
                taxRatesOccurs.put(taxRateCodeMapping, taxRateOccurs + 1);
            });

        BigDecimal gotTheMostCommonTaxRateValue = BigDecimal.valueOf(
            taxRatesOccurs.entrySet().stream()
            .max((entry1, entry2) -> entry1.getValue() - entry2.getValue())
            .get().getKey().getValue()
        );

        return LineItem.builder()
            .quantity(1)
            .originalPrice(new Cost(allegroDelivery.getCost().getAmount(), Currency.PLN))
            .price(new Cost(allegroDelivery.getCost().getAmount(), Currency.PLN))
            .offer(new Offer("Dostawa do klienta"))
            .boughtAt(allegroDelivery.getTime().getFrom())
            .tax(new Tax(gotTheMostCommonTaxRateValue, "", ""))
            .build();
    }

    private static BigDecimal getTaxRatePercentage(LineItem allegroLineItem){

        if(allegroLineItem.getTax() == null){

            return BigDecimal.valueOf(23);
        }

        return allegroLineItem.getTax().getRate();
    }
}
