package pl.kamil_dywan.factory;

import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.subiektgt.generated.TaxRate;
import pl.kamil_dywan.external.subiektgt.generated.Type;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.LineItemQuantity;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.LineTax;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.PercentDiscount;
import pl.kamil_dywan.external.subiektgt.own.Code;
import pl.kamil_dywan.external.subiektgt.own.LineItemMoneyStats;
import pl.kamil_dywan.external.subiektgt.own.TaxRateCodeMapping;
import pl.kamil_dywan.external.subiektgt.own.UOMCode;

import java.math.BigDecimal;

public class InvoiceLineFactory {

    private InvoiceLineFactory(){


    }

    public static LineItemQuantity createQuantity(Integer packSize, BigDecimal amount){

        return LineItemQuantity.builder()
            .packsize(packSize)
            .amount(amount.intValue())
            .uomCode(UOMCode.UNIT)
            .build();
    }

    public static PercentDiscount createDiscount(){

        return new PercentDiscount(
            new Type("", Code.LID),
            BigDecimal.ZERO
        );
    }

    public static LineTax createLineTax(BigDecimal tax, TaxRateCodeMapping taxRateCodeMapping){

        TaxRate taxRate = new TaxRate(
            tax,
            taxRateCodeMapping.getCode()
        );

        return LineTax.builder()
            .taxRate(taxRate)
            .taxValue(tax)
            .build();
    }

    public static LineItemMoneyStats getLineItemMoneyStats(LineItem allegroLineItem){

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

        return new LineItemMoneyStats(
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
