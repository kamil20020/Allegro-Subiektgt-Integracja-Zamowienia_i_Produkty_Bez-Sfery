package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.factory.InvoiceLineFactory;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLine;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.LineItemQuantity;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.LineTax;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.UnitPriceHolder;
import pl.kamil_dywan.external.subiektgt.own.LineItemMoneyStats;
import pl.kamil_dywan.external.subiektgt.own.TaxRateCodeMapping;
import pl.kamil_dywan.external.subiektgt.own.UOMCode;

import java.math.BigDecimal;

public class InvoiceLineMapper {

    private InvoiceLineMapper(){


    }

    public static InvoiceLine map(Integer invoiceLineNumber, LineItem allegroLineItem, LineItemMoneyStats lineItemMoneyStats){

        Offer allegroOffer = allegroLineItem.getOffer();

        Integer taxRateValue = lineItemMoneyStats.taxRatePercentage().intValue();

        TaxRateCodeMapping taxRateCodeMapping = TaxRateCodeMapping.getByValue(taxRateValue);

        LineItemQuantity quantity = map(allegroLineItem);
        LineTax lineTax = InvoiceLineFactory.createLineTax(lineItemMoneyStats.totalTaxValue(), taxRateCodeMapping);

        return InvoiceLine.builder()
            .lineNumber(invoiceLineNumber)
            .product(ProductMapper.map(allegroOffer))
            .quantity(quantity)
            .unitPrice(new UnitPriceHolder(lineItemMoneyStats.unitPriceWithoutTax()))
            .percentDiscount(InvoiceLineFactory.createDiscount())
            .lineTax(lineTax)
            .lineTotal(lineItemMoneyStats.totalPriceWithTax())
            .invoiceLineInformation(allegroOffer.getName())
            .build();
    }

    private static LineItemQuantity map(LineItem allegroLineItem){

        Offer allegroOffer = allegroLineItem.getOffer();

        BigDecimal quantityValue = BigDecimal.valueOf(allegroLineItem.getQuantity());
        Integer packSize = allegroOffer.getProductSet().getProducts().size();

        return LineItemQuantity.builder()
            .packsize(packSize)
            .amount(quantityValue.intValue())
            .uomCode(UOMCode.UNIT)
            .build();
    }
}
