package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLine;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLineQuantity;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.LineTax;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.UnitPriceHolder;
import pl.kamil_dywan.external.subiektgt.own.InvoiceLineMoneyStats;
import pl.kamil_dywan.external.subiektgt.own.TaxRateCodeMapping;
import pl.kamil_dywan.factory.LineTaxFactory;
import pl.kamil_dywan.factory.PercentDiscountFactory;

import java.math.BigDecimal;

public class InvoiceLineMapper {

    private InvoiceLineMapper(){


    }

    public static InvoiceLine map(Integer invoiceLineNumber, LineItem allegroLineItem, InvoiceLineMoneyStats lineItemMoneyStats){

        Offer allegroOffer = allegroLineItem.getOffer();

        Integer taxRateValue = lineItemMoneyStats.taxRatePercentage().intValue();

        TaxRateCodeMapping taxRateCodeMapping = TaxRateCodeMapping.getByValue(taxRateValue);

        InvoiceLineQuantity quantity = InvoiceLineQuantityMapper.map(allegroLineItem);
        LineTax lineTax = LineTaxFactory.create(lineItemMoneyStats.totalTaxValue(), taxRateCodeMapping);

        return InvoiceLine.builder()
            .lineNumber(invoiceLineNumber)
            .product(ProductMapper.map(allegroOffer))
            .quantity(quantity)
            .unitPrice(new UnitPriceHolder(lineItemMoneyStats.unitPriceWithoutTax()))
            .percentDiscount(PercentDiscountFactory.create())
            .lineTax(lineTax)
            .lineTotal(lineItemMoneyStats.totalPriceWithTax())
            .invoiceLineInformation(allegroOffer.getName())
            .build();
    }

    public static InvoiceLineMoneyStats getLineItemMoneyStats(LineItem allegroLineItem){

        BigDecimal quantityValue = BigDecimal.valueOf(allegroLineItem.getQuantity());

        BigDecimal taxRatePercentage = getTaxRatePercentage(allegroLineItem);
        BigDecimal taxRateValue = taxRatePercentage.divide(BigDecimal.valueOf(100));

        BigDecimal unitPriceWithTax = allegroLineItem.getPrice().getAmount();
        BigDecimal unitPriceWithoutTax = unitPriceWithTax.multiply(
            BigDecimal.ONE.subtract(taxRateValue)
        );

        BigDecimal totalPriceWithTax = unitPriceWithTax.multiply(quantityValue);
        BigDecimal totalPriceWithoutTax = unitPriceWithoutTax.multiply(quantityValue);
        BigDecimal totalTaxValue = totalPriceWithTax.subtract(totalPriceWithoutTax);

        return new InvoiceLineMoneyStats(
            taxRatePercentage,
            unitPriceWithTax,
            unitPriceWithoutTax,
            totalPriceWithTax,
            totalPriceWithoutTax,
            totalTaxValue
        );
    }

    private static BigDecimal getTaxRatePercentage(LineItem allegroLineItem){

        if(allegroLineItem.getTax() == null){

            return BigDecimal.ZERO;
        }

        return allegroLineItem.getTax().getRate();
    }
}
