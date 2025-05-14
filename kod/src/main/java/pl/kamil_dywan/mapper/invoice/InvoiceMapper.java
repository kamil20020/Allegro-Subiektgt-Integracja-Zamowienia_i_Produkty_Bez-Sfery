package pl.kamil_dywan.mapper.invoice;

import pl.kamil_dywan.external.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.external.allegro.generated.order_item.OrderItem;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.own.order.OrderMoneyStats;
import pl.kamil_dywan.external.allegro.own.order.OrderTaxSummary;
import pl.kamil_dywan.external.allegro.own.order.OrderTotalMoneyStats;
import pl.kamil_dywan.factory.InvoiceHeadFactory;
import pl.kamil_dywan.external.subiektgt.generated.Invoice;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceTotal;
import pl.kamil_dywan.external.subiektgt.generated.TaxSubTotal;
import pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLine;
import pl.kamil_dywan.external.subiektgt.own.Code;
import pl.kamil_dywan.external.allegro.own.order.OrderItemMoneyStats;
import pl.kamil_dywan.factory.InvoiceReferencesFactory;
import pl.kamil_dywan.mapper.AllegroOrderItemMapper;
import pl.kamil_dywan.mapper.TaxSubTotalMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface InvoiceMapper {

    static Invoice map(Order allegroOrder){

        pl.kamil_dywan.external.allegro.generated.invoice.Invoice allegroInvoice = allegroOrder.getInvoice();
        Delivery allegroDelivery = allegroOrder.getDelivery();

        LocalDate invoiceDate = allegroOrder.getPayment().getFinishedAt().toLocalDate();
        String invoiceCity = allegroInvoice.getAddress().getCity();
        Buyer buyer = BuyerMapper.map(allegroInvoice.getAddress());

        OrderMoneyStats allegroOrderMoneyStats = allegroOrder.getMoneySummary();
        List<OrderItemMoneyStats> orderItemsMoneyStats = allegroOrderMoneyStats.orderItemsMoneyStats();
        List<OrderTaxSummary> orderTaxesSummaries = allegroOrderMoneyStats.orderTaxesSummaries();
        OrderTotalMoneyStats orderTotalMoneyStats = allegroOrderMoneyStats.orderTotalMoneyStats();

        List<OrderItem> allegroOrderItems = new ArrayList<>(allegroOrder.getOrderItems());

        allegroOrderItems.add(AllegroOrderItemMapper.mapDeliveryToLineItem(allegroDelivery));

        List<InvoiceLine> subiektInvoiceLines = new ArrayList<>();

        for(int i=0; i < allegroOrderItems.size(); i++){

            Integer newOrderItemNumber = i + 1;
            OrderItem orderItem = allegroOrderItems.get(i);
            OrderItemMoneyStats orderItemMoneyStats = orderItemsMoneyStats.get(i);

            InvoiceLine subiektInvoiceLine = InvoiceLineMapper.map(newOrderItemNumber, orderItem, orderItemMoneyStats);

            subiektInvoiceLines.add(subiektInvoiceLine);
        }

        int deliveryIndex = subiektInvoiceLines.size() - 1;

        subiektInvoiceLines.get(deliveryIndex).getProduct().setSuppliersProductCode("DOSTAWA123");

        List<TaxSubTotal> taxSubTotals = orderTaxesSummaries.stream()
            .map(TaxSubTotalMapper::map)
            .collect(Collectors.toList());

        InvoiceTotal invoiceTotal = InvoiceTotalMapper.map(orderTotalMoneyStats);

        return Invoice.builder()
            .invoiceHead(InvoiceHeadFactory.create(Code.PLN))
            .invoiceDate(invoiceDate)
            .invoiceReferences(InvoiceReferencesFactory.create())
            .cityOfIssue(invoiceCity)
            .taxPointDate(allegroInvoice.getDueDate())
            .buyer(buyer)
            .invoiceLines(subiektInvoiceLines)
            .narrative("")
            .specialInstructions("dokument liczony wg cen netto")
            .settlement(null)
            .taxSubTotals(taxSubTotals)
            .invoiceTotal(invoiceTotal)
            .build();
    }
}
