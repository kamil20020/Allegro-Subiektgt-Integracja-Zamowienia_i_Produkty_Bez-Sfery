package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.*;
import pl.kamil_dywan.external.subiektgt.own.invoice.InvoiceLineMoneyStats;
import pl.kamil_dywan.external.subiektgt.own.product.TaxRateCodeMapping;
import pl.kamil_dywan.factory.LineTaxFactory;
import pl.kamil_dywan.factory.PercentDiscountFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface InvoiceLineMapper {

    static InvoiceLine map(Integer invoiceLineNumber, LineItem allegroLineItem, InvoiceLineMoneyStats invoiceLineMoneyStats){

        Offer allegroOffer = allegroLineItem.getOffer();

        Integer taxRateValue = invoiceLineMoneyStats.taxRatePercentage().intValue();

        TaxRateCodeMapping taxRateCodeMapping = TaxRateCodeMapping.getByValue(taxRateValue);

        InvoiceLineQuantity quantity = InvoiceLineQuantityMapper.map(allegroLineItem);
        LineTax lineTax = LineTaxFactory.create(invoiceLineMoneyStats.totalTaxValue(), taxRateCodeMapping);

        return InvoiceLine.builder()
            .lineNumber(invoiceLineNumber)
            .product(ProductMapper.map(allegroOffer))
            .quantity(quantity)
            .unitPrice(new UnitPriceHolder(invoiceLineMoneyStats.unitPriceWithoutTax()))
            .percentDiscount(PercentDiscountFactory.create())
            .lineTax(lineTax)
            .lineTotal(invoiceLineMoneyStats.totalPriceWithTax())
            .invoiceLineInformation(allegroOffer.getName())
            .build();
    }

    static InvoiceLineMoneyStats getInvoiceItemMoneyStats(LineItem allegroLineItem){

        BigDecimal quantityValue = BigDecimal.valueOf(allegroLineItem.getQuantity());

        BigDecimal taxRatePercentage = getTaxRatePercentage(allegroLineItem);
        BigDecimal taxRateValue = taxRatePercentage.divide(
            BigDecimal.valueOf(100),
            RoundingMode.HALF_UP
        );

        BigDecimal unitPriceWithTax = allegroLineItem.getPrice().getAmount();
        BigDecimal unitPriceWithoutTax = unitPriceWithTax.divide(
            BigDecimal.ONE.add(taxRateValue),
            RoundingMode.HALF_UP
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

            return BigDecimal.valueOf(23);
        }

        return allegroLineItem.getTax().getRate();
    }
}
