package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.factory.InvoiceHeadFactory;
import pl.kamil_dywan.external.subiektgt.generated.Invoice;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceTotal;
import pl.kamil_dywan.external.subiektgt.generated.TaxSubTotal;
import pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLine;
import pl.kamil_dywan.external.subiektgt.generated.supplier.Supplier;
import pl.kamil_dywan.external.subiektgt.own.Code;
import pl.kamil_dywan.external.subiektgt.own.InvoiceLineMoneyStats;
import pl.kamil_dywan.external.subiektgt.own.TaxRateCodeMapping;
import pl.kamil_dywan.factory.SettlementFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedHashMap;
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

        List<LineItem> allegroLineItems = allegroOrder.getLineItems();

//        allegroLineItems.add(AllegroLineItemMapper.mapDeliveryToLineItem(allegroOrder));

        List<InvoiceLine> invoiceLines = allegroLineItems.stream()
            .map(allegroLineItem -> {

                Integer newLineItemNumber = lineItemNumber.incrementAndGet();
                InvoiceLineMoneyStats lineItemMoneyStats = InvoiceLineMapper.getInvoiceItemMoneyStats(allegroLineItem);

                InvoiceLine invoiceLine = InvoiceLineMapper.map(newLineItemNumber, allegroLineItem, lineItemMoneyStats);
                invoiceLine.scale(2, RoundingMode.HALF_UP);

                BigDecimal taxRatePercentage = lineItemMoneyStats.taxRatePercentage();
                updateTaxSubTotal(taxSubtotalsMappings, taxRatePercentage, lineItemMoneyStats);

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
            .settlement(SettlementFactory.create(allegroInvoice.getDueDate()))
            .taxSubTotals(taxSubTotals)
            .invoiceTotal(invoiceTotal)
            .build();
    }

    private static void updateTaxSubTotal(
        Map<TaxRateCodeMapping, TaxSubTotal> taxSubtotalsMappings,
        BigDecimal taxRatePercentage,
        InvoiceLineMoneyStats lineItemMoneyStats
    ){
        TaxRateCodeMapping taxRateCodeMapping = TaxRateCodeMapping.getByValue(taxRatePercentage);
        TaxSubTotal taxSubTotalForTaxRate = taxSubtotalsMappings.get(taxRateCodeMapping);

        taxSubTotalForTaxRate.update(
            lineItemMoneyStats.totalPriceWithoutTax(),
            lineItemMoneyStats.totalTaxValue(),
            lineItemMoneyStats.totalPriceWithTax()
        );
    }

    private static void addProportionalDeliveryCost(
        Order allegroOrder,
        Map<TaxRateCodeMapping, TaxSubTotal> taxSubtotalsMappings,
        BigDecimal totalOrderPriceWithoutTax
    ){
        BigDecimal totalDeliveryCostWithTax = allegroOrder.getDelivery().getCost().getAmount();

        taxSubtotalsMappings.values()
            .forEach(taxSubTotal -> {

                BigDecimal priceWithoutTax = taxSubTotal.getTaxableValueAtRate();

                BigDecimal taxRatePercentage = taxSubTotal.getTaxRate().getValue();
                BigDecimal taxRateValue = taxRatePercentage.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
                BigDecimal taxRateValuePlusOne = BigDecimal.ONE.add(taxRateValue);

                BigDecimal taxPartInOrder = priceWithoutTax.divide(totalOrderPriceWithoutTax, RoundingMode.HALF_UP);
                BigDecimal deliveryCostForTaxWithTax = totalDeliveryCostWithTax.multiply(taxPartInOrder);
                BigDecimal deliveryCostForTaxWithoutTax = deliveryCostForTaxWithTax.divide(taxRateValuePlusOne, RoundingMode.HALF_UP);
                BigDecimal deliveryTax = deliveryCostForTaxWithTax.subtract(deliveryCostForTaxWithoutTax);

                taxSubTotal.update(deliveryCostForTaxWithoutTax, deliveryTax, deliveryCostForTaxWithTax);
            });
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
}
