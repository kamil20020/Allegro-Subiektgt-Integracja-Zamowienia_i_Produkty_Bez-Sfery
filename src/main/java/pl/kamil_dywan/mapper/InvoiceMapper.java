package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.factory.InvoiceHeadFactory;
import pl.kamil_dywan.factory.InvoiceLineFactory;
import pl.kamil_dywan.external.subiektgt.generated.Invoice;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceTotal;
import pl.kamil_dywan.external.subiektgt.generated.TaxSubTotal;
import pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLine;
import pl.kamil_dywan.external.subiektgt.generated.settlement.Settlement;
import pl.kamil_dywan.external.subiektgt.generated.settlement.SettlementTerms;
import pl.kamil_dywan.external.subiektgt.generated.supplier.Supplier;
import pl.kamil_dywan.external.subiektgt.own.Code;
import pl.kamil_dywan.external.subiektgt.own.LineItemMoneyStats;
import pl.kamil_dywan.external.subiektgt.own.TaxRateCodeMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InvoiceMapper {

    private InvoiceMapper(){


    }

    public static Invoice map(Order allegroOrder){

        pl.kamil_dywan.external.allegro.generated.invoice.Invoice allegroInvoice = allegroOrder.getInvoice();

        LocalDate invoiceDate = allegroOrder.getPayment().getFinishedAt().toLocalDate();
        String invoiceCity = allegroInvoice.getAddress().getCity();
        Supplier supplier = SupplierMapper.map(allegroInvoice);
        Buyer buyer = BuyerMapper.map(allegroOrder.getBuyer());

        AtomicInteger lineItemNumber = new AtomicInteger(0);

        Map<TaxRateCodeMapping, TaxSubTotal> taxSubtotalsMappings = TaxSubTotal.getEmptyMappingsForAllTaxesRates();

        InvoiceTotal invoiceTotal = InvoiceTotal.getEmpty();

        List<InvoiceLine> invoiceLines = allegroOrder.getLineItems().stream()
            .map(allegroLineItem -> {

                Integer newLineItemNumber = lineItemNumber.incrementAndGet();
                LineItemMoneyStats lineItemMoneyStats = InvoiceLineFactory.getLineItemMoneyStats(allegroLineItem);

                BigDecimal taxRatePercentage = lineItemMoneyStats.taxRatePercentage();
                TaxRateCodeMapping taxRateCodeMapping = TaxRateCodeMapping.getByValue(taxRatePercentage);
                updateTaxSubTotal(taxSubtotalsMappings, taxRateCodeMapping, lineItemMoneyStats);

                InvoiceLine invoiceLine = InvoiceLineMapper.map(newLineItemNumber, allegroLineItem, lineItemMoneyStats);
                invoiceLine.scale(2, RoundingMode.HALF_UP);

                return invoiceLine;
            })
            .collect(Collectors.toList());

        invoiceTotal.setNumberOfLines(invoiceLines.size());
        updateInvoiceTotal(invoiceTotal, taxSubtotalsMappings);

        scaleTaxesSubTotalsMappings(taxSubtotalsMappings, 2, RoundingMode.HALF_UP);
        invoiceTotal.scale(2, RoundingMode.HALF_UP);

        List<TaxSubTotal> taxSubTotals = taxSubtotalsMappings.values().stream()
            .toList();

        return Invoice.builder()
            .invoiceHead(InvoiceHeadFactory.create(Code.PLN))
            .invoiceDate(invoiceDate)
            .cityOfIssue(invoiceCity)
            .taxPointDate(allegroInvoice.getDueDate())
            .supplier(supplier)
            .buyer(buyer)
            .invoiceLines(invoiceLines)
            .narrative("FS - płatność gotówka karta kredyt przelew i kredyt kupiecki")
            .specialInstructions("dokument liczony wg cen netto")
            .settlement(InvoiceMapper.create(allegroInvoice.getDueDate()))
            .taxSubTotals(taxSubTotals)
            .invoiceTotal(invoiceTotal)
            .build();
    }

    private static void updateTaxSubTotal(
        Map<TaxRateCodeMapping, TaxSubTotal> taxSubtotalsMappings,
        TaxRateCodeMapping taxRateCodeMapping,
        LineItemMoneyStats lineItemMoneyStats
    ){
        TaxSubTotal taxSubTotalForTaxRate = taxSubtotalsMappings.get(taxRateCodeMapping);

        taxSubTotalForTaxRate.update(
            lineItemMoneyStats.totalPriceWithoutTax(),
            lineItemMoneyStats.totalTaxValue(),
            lineItemMoneyStats.totalPriceWithTax()
        );
    }

    private static void updateInvoiceTotal(
            InvoiceTotal invoiceTotal,
            Map<TaxRateCodeMapping, TaxSubTotal> taxSubtotalsMappings
    ){
        invoiceTotal.setNumberOfTaxRates(TaxSubTotal.getNumberOfPresentTaxSubTotals(taxSubtotalsMappings));

        taxSubtotalsMappings.values()
            .forEach(taxSubTotal -> {

                invoiceTotal.update(
                    taxSubTotal.getTaxableValueAtRate(),
                    taxSubTotal.getTaxAtRate(),
                    taxSubTotal.getNetPaymentAtRate()
                );
            });
    }

    private static void scaleTaxesSubTotalsMappings(
        Map<TaxRateCodeMapping, TaxSubTotal> taxSubtotalsMappings,
        int scale,
        RoundingMode roundingMode
    ){
        taxSubtotalsMappings.values()
            .forEach(taxSubTotal -> {

                taxSubTotal.scale(scale, roundingMode);
            });
    }

    public static Settlement create(LocalDate dueDate){

        return new Settlement(
            SettlementTerms.builder()
                .value(dueDate)
                .code(Code.Code14I)
                .build()
        );
    }
}
