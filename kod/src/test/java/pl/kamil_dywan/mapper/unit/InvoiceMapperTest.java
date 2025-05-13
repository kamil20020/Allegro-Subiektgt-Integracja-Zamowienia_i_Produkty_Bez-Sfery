package pl.kamil_dywan.mapper.unit;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kamil_dywan.external.allegro.generated.Cost;
import pl.kamil_dywan.external.allegro.generated.Payment;
import pl.kamil_dywan.external.allegro.generated.buyer.Buyer;
import pl.kamil_dywan.external.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.external.allegro.generated.delivery.DeliveryTime;
import pl.kamil_dywan.external.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.own.Currency;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceTotal;
import pl.kamil_dywan.external.subiektgt.generated.TaxRate;
import pl.kamil_dywan.external.subiektgt.generated.TaxSubTotal;
import pl.kamil_dywan.external.subiektgt.generated.invoice_head.InvoiceHead;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLine;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.LineTax;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.UnitPriceHolder;
import pl.kamil_dywan.external.subiektgt.generated.settlement.Settlement;
import pl.kamil_dywan.external.subiektgt.generated.supplier.Supplier;
import pl.kamil_dywan.external.subiektgt.own.Code;
import pl.kamil_dywan.external.subiektgt.own.invoice.InvoiceLineMoneyStats;
import pl.kamil_dywan.external.subiektgt.own.product.TaxRateCodeMapping;
import pl.kamil_dywan.factory.InvoiceHeadFactory;
import pl.kamil_dywan.factory.SettlementFactory;
import pl.kamil_dywan.mapper.*;
import pl.kamil_dywan.mapper.invoice.BuyerMapper;
import pl.kamil_dywan.mapper.invoice.InvoiceLineMapper;
import pl.kamil_dywan.mapper.invoice.InvoiceMapper;
import pl.kamil_dywan.mapper.invoice.SupplierMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class InvoiceMapperTest {

    @Test
    void shouldMap() {

        InvoiceAddress allegroInvoiceAddress = InvoiceAddress.builder()
            .city("City 123")
            .build();

        Payment allegroPayment = Payment.builder()
            .finishedAt(OffsetDateTime.now())
            .build();

        Invoice allegroInvoice = Invoice.builder()
            .address(allegroInvoiceAddress)
            .dueDate(LocalDate.now())
            .build();

        Buyer allegroBuyer = Buyer.builder()
            .build();

        Delivery allegroDelivery = Delivery.builder()
            .cost(new Cost(new BigDecimal("0.00"), Currency.PLN))
            .time(new DeliveryTime(OffsetDateTime.now(), null, null, null))
            .build();

        LineItem lineItem1 = LineItem.builder()
            .quantity(1)
            .build();

        LineItem lineItem2 = LineItem.builder()
            .quantity(2)
            .build();

        LineItem lineItem3 = LineItem.builder()
            .quantity(3)
            .build();

        LineItem lineItem4 = LineItem.builder()
            .quantity(4)
            .build();

        LineItem lineItem5 = LineItem.builder()
            .quantity(5)
            .build();

        LineItem deliveryLineItem = LineItem.builder()
            .quantity(6)
            .build();

        List<LineItem> allegroLineItems = new ArrayList<>(List.of(lineItem1, lineItem2, lineItem3, lineItem4, lineItem5));

        // Item x 70 -> for 1 netto 119,10 PLN, brutto 146,49 PLN, tax 23%, tax value 27,39 PLN
        InvoiceLineMoneyStats invoiceLineMoneyStats1 = new InvoiceLineMoneyStats(
            new BigDecimal("23.00"),
            new BigDecimal("146.49"),
            new BigDecimal("119.10"),
            new BigDecimal("10254.51"),
            new BigDecimal("8337.00"),
            new BigDecimal("1917.51")
        );

        // Item x 70 -> for 1 netto 35.98 PLN, brutto 35.98 PLN, tax 0%, tax value 0 PLN
        InvoiceLineMoneyStats invoiceLineMoneyStats2 = new InvoiceLineMoneyStats(
            new BigDecimal("0.00"),
            new BigDecimal("35.98"),
            new BigDecimal("35.98"),
            new BigDecimal("2518.60"),
            new BigDecimal("2518.60"),
            new BigDecimal("0.00")
        );

        // Item x 50 -> for 1 netto 324,10 PLN, brutto 349,92 PLN, tax 23%, tax value 25.82 PLN
        InvoiceLineMoneyStats invoiceLineMoneyStats3 = new InvoiceLineMoneyStats(
            new BigDecimal("8.00"),
            new BigDecimal("349.92"),
            new BigDecimal("324.00"),
            new BigDecimal("17496.00"),
            new BigDecimal("16200.00"),
            new BigDecimal("1296.00")
        );

        // Item x 50 -> for 1 netto 171,00 PLN, brutto 171,00 PLN, tax 0%, tax value 0 PLN
        InvoiceLineMoneyStats invoiceLineMoneyStats4 = new InvoiceLineMoneyStats(
            new BigDecimal("0.00"),
            new BigDecimal("171.00"),
            new BigDecimal("171.00"),
            new BigDecimal("8550.00"),
            new BigDecimal("8550.00"),
            new BigDecimal("0.00")
        );

        // Item x 50 -> for 1 netto 396,00 PLN, brutto 427,68 PLN, tax 8%, tax value 31,68 PLN
        InvoiceLineMoneyStats invoiceLineMoneyStats5 = new InvoiceLineMoneyStats(
            new BigDecimal("8.00"),
            new BigDecimal("396.00"),
            new BigDecimal("427.68"),
            new BigDecimal("21384.00"),
            new BigDecimal("19800.00"),
            new BigDecimal("1584.00")
        );

        InvoiceLineMoneyStats deliveryInvoiceLineMoneyStats = new InvoiceLineMoneyStats(
            new BigDecimal("23.00"),
            new BigDecimal("20.00"),
            new BigDecimal("18.52"),
            new BigDecimal("20.00"),
            new BigDecimal("18.52"),
            new BigDecimal("1.48")
        );

        List<InvoiceLineMoneyStats> invoiceLineMoneyStatsLists = List.of(
            invoiceLineMoneyStats1,
            invoiceLineMoneyStats2,
            invoiceLineMoneyStats3,
            invoiceLineMoneyStats4,
            invoiceLineMoneyStats5
        );

        Order allegroOrder = Order.builder()
            .payment(allegroPayment)
            .invoice(allegroInvoice)
            .buyer(allegroBuyer)
            .delivery(allegroDelivery)
            .lineItems(allegroLineItems)
            .build();

        Supplier expectedSupplier = new Supplier();
        pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer expectedBuyer = new pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer();

        InvoiceLine expectedInvoiceLine = InvoiceLine.builder()
            .unitPrice(new UnitPriceHolder(BigDecimal.ONE))
            .lineTax(new LineTax(null, BigDecimal.ONE))
            .lineTotal(BigDecimal.ONE)
            .build();

        InvoiceHead expectedInvoiceHead = new InvoiceHead();
        Settlement expectedSettlement = null;

        TaxSubTotal expectedTaxSubTotal23 = TaxSubTotal.builder()
            .code(Code.PLN)
            .taxRate(new TaxRate(BigDecimal.valueOf(23), TaxRateCodeMapping.H.getCode()))
            .taxableValueAtRate(new BigDecimal("8355.52"))
            .taxAtRate(new BigDecimal("1918.99"))
            .netPaymentAtRate(new BigDecimal("10274.51"))
            .grossPaymentAtRate(new BigDecimal("10274.51"))
            .build();

        TaxSubTotal expectedTaxSubTotal8 = TaxSubTotal.builder()
            .code(Code.PLN)
            .taxRate(new TaxRate(BigDecimal.valueOf(8), TaxRateCodeMapping.L.getCode()))
            .taxableValueAtRate(new BigDecimal("36000.00"))
            .taxAtRate(new BigDecimal("2880.00"))
            .netPaymentAtRate(new BigDecimal("38880.00"))
            .grossPaymentAtRate(new BigDecimal("38880.00"))
            .build();

        TaxSubTotal expectedTaxSubTotal0 = TaxSubTotal.builder()
            .code(Code.PLN)
            .taxRate(new TaxRate(BigDecimal.valueOf(0), TaxRateCodeMapping.Z.getCode()))
            .taxableValueAtRate(new BigDecimal("11068.60"))
            .taxAtRate(new BigDecimal("0.00"))
            .netPaymentAtRate(new BigDecimal("11068.60"))
            .grossPaymentAtRate(new BigDecimal("11068.60"))
            .build();

        List<TaxSubTotal> expectedTaxSubTotals = List.of(
            expectedTaxSubTotal23, expectedTaxSubTotal8, expectedTaxSubTotal0
        );

        //when
        try(
                MockedStatic<SupplierMapper> mockedSupplierMapper = Mockito.mockStatic(SupplierMapper.class);
                MockedStatic<BuyerMapper> mockedBuyerMapper = Mockito.mockStatic(BuyerMapper.class);
                MockedStatic<InvoiceLineMapper> mockedInvoiceLineMapper = Mockito.mockStatic(InvoiceLineMapper.class);
                MockedStatic<AllegroLineItemMapper> mockedAllegroLineItemMapper = Mockito.mockStatic(AllegroLineItemMapper.class);
                MockedStatic<InvoiceHeadFactory> mockedInvoiceHeadFactory = Mockito.mockStatic(InvoiceHeadFactory.class);
                MockedStatic<SettlementFactory> mockedSettlementFactory = Mockito.mockStatic(SettlementFactory.class)
        ){
            mockedSupplierMapper.when(() -> SupplierMapper.map(any())).thenReturn(expectedSupplier);
            mockedBuyerMapper.when(() -> BuyerMapper.map(any())).thenReturn(expectedBuyer);
            mockedInvoiceLineMapper.when(() -> InvoiceLineMapper.map(any(), any(), any())).thenReturn(expectedInvoiceLine);

            for(int i=0; i < allegroLineItems.size(); i++){

                LineItem lineItem = allegroLineItems.get(i);
                InvoiceLineMoneyStats invoiceLineMoneyStats = invoiceLineMoneyStatsLists.get(i);

                mockedInvoiceLineMapper.when(() -> InvoiceLineMapper.getInvoiceItemMoneyStats(eq(lineItem))).thenReturn(invoiceLineMoneyStats);
            }

            mockedInvoiceLineMapper.when(() -> InvoiceLineMapper.getInvoiceItemMoneyStats(eq(deliveryLineItem))).thenReturn(deliveryInvoiceLineMoneyStats);
            mockedAllegroLineItemMapper.when(() -> AllegroLineItemMapper.mapDeliveryToLineItem(any())).thenReturn(deliveryLineItem);

            mockedInvoiceHeadFactory.when(() -> InvoiceHeadFactory.create(any())).thenReturn(expectedInvoiceHead);
            mockedSettlementFactory.when(() -> SettlementFactory.create(any())).thenReturn(expectedSettlement);

            pl.kamil_dywan.external.subiektgt.generated.Invoice gotInvoice = InvoiceMapper.map(allegroOrder);

            //then
            assertNotNull(gotInvoice);
            assertNotNull(gotInvoice.getInvoiceLines());
            assertNotNull(gotInvoice.getTaxSubTotals());
            assertNotNull(gotInvoice.getInvoiceTotal());
            assertEquals(expectedInvoiceHead, gotInvoice.getInvoiceHead());
            assertEquals(allegroPayment.getFinishedAt().toLocalDate(), gotInvoice.getInvoiceDate());
            assertEquals(allegroInvoiceAddress.getCity(), gotInvoice.getCityOfIssue());
            assertEquals(allegroInvoice.getDueDate(), gotInvoice.getTaxPointDate());
            assertEquals(expectedSupplier, gotInvoice.getSupplier());
            assertEquals(expectedBuyer, gotInvoice.getBuyer());
            assertTrue(gotInvoice.getInvoiceLines().contains(expectedInvoiceLine));
            assertEquals("", gotInvoice.getNarrative());
            assertEquals("dokument liczony wg cen netto", gotInvoice.getSpecialInstructions());
            assertEquals(expectedSettlement, gotInvoice.getSettlement());

            List<TaxSubTotal> gotTaxSubTotals = gotInvoice.getTaxSubTotals();

            for(int i = 0; i < expectedTaxSubTotals.size(); i++){

                TaxSubTotal expectedTaxSubTotal = expectedTaxSubTotals.get(i);
                TaxSubTotal gotTaxSubTotal = gotTaxSubTotals.get(i);

                assertEquals(expectedTaxSubTotal, gotTaxSubTotal);
            }

            InvoiceTotal gotInvoiceTotal = gotInvoice.getInvoiceTotal();

            assertEquals(6, gotInvoiceTotal.getNumberOfLines());
            assertEquals(3, gotInvoiceTotal.getNumberOfTaxRates());
            assertEquals(new BigDecimal("55424.12"), gotInvoiceTotal.getLineValueTotal());
            assertEquals(new BigDecimal("55424.12"), gotInvoiceTotal.getTaxableTotal());
            assertEquals(new BigDecimal("4798.99"), gotInvoiceTotal.getTaxTotal());
            assertEquals(new BigDecimal("60223.11"), gotInvoiceTotal.getNetPaymentTotal());
            assertEquals(new BigDecimal("60223.11"), gotInvoiceTotal.getGrossPaymentTotal());

            mockedSupplierMapper.verify(() -> SupplierMapper.map(allegroInvoice));
            mockedBuyerMapper.verify(() -> BuyerMapper.map(allegroBuyer));

            for(int i=0; i < allegroLineItems.size() - 1; i++){

                LineItem allegroLineItem = allegroLineItems.get(i);
                InvoiceLineMoneyStats invoiceLineMoneyStats = invoiceLineMoneyStatsLists.get(i);

                int finalI = i + 1;

                mockedInvoiceLineMapper.verify(() -> InvoiceLineMapper.map(finalI, allegroLineItem, invoiceLineMoneyStats));
                mockedInvoiceLineMapper.verify(() -> InvoiceLineMapper.getInvoiceItemMoneyStats(allegroLineItem));
            }

            mockedInvoiceLineMapper.verify(() -> InvoiceLineMapper.getInvoiceItemMoneyStats(deliveryLineItem));
            mockedAllegroLineItemMapper.verify(() -> AllegroLineItemMapper.mapDeliveryToLineItem(allegroDelivery));

            mockedInvoiceHeadFactory.verify(() -> InvoiceHeadFactory.create(Code.PLN));
//            mockedSettlementFactory.verify(() -> SettlementFactory.create(allegroInvoice.getDueDate()));
        }
    }
}