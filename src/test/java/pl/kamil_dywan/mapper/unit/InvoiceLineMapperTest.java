package pl.kamil_dywan.mapper.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kamil_dywan.external.allegro.generated.Cost;
import pl.kamil_dywan.external.allegro.generated.Payment;
import pl.kamil_dywan.external.allegro.generated.buyer.Buyer;
import pl.kamil_dywan.external.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.external.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Tax;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.own.Currency;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.*;
import pl.kamil_dywan.external.subiektgt.generated.supplier.Supplier;
import pl.kamil_dywan.external.subiektgt.own.Code;
import pl.kamil_dywan.external.subiektgt.own.InvoiceLineMoneyStats;
import pl.kamil_dywan.external.subiektgt.own.TaxRateCodeMapping;
import pl.kamil_dywan.factory.LineTaxFactory;
import pl.kamil_dywan.factory.PercentDiscountFactory;
import pl.kamil_dywan.mapper.InvoiceLineMapper;
import pl.kamil_dywan.mapper.InvoiceLineQuantityMapper;
import pl.kamil_dywan.mapper.ProductMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class InvoiceLineMapperTest {

    @Test
    void shouldMap() {

        //given
        Integer invoiceLineNumber = 1;

        Offer allegroOffer = Offer.builder()
            .name("Offer 123")
            .build();

        LineItem allegroLineItem = LineItem.builder()
            .offer(allegroOffer)
            .build();

        // Mouse x 2 -> for 1 netto 30 PLN, brutto 36,9 PLN, tax 6,9 PLN
        InvoiceLineMoneyStats invoiceLineMoneyStats = new InvoiceLineMoneyStats(
            new BigDecimal("23.00"),
            new BigDecimal("36.90"),
            new BigDecimal("30.00"),
            new BigDecimal("73.80"),
            new BigDecimal("60.00"),
            new BigDecimal("13.80")
        );

        InvoiceLineQuantity expectedInvoiceLineQuantity = new InvoiceLineQuantity();
        LineTax expectedLineTax = new LineTax();
        Product expectedProduct = new Product();
        PercentDiscount expectedPercentDiscount = new PercentDiscount();

        //when
        try(
            MockedStatic<InvoiceLineQuantityMapper> mockedInvoiceLineQuantityMapper = Mockito.mockStatic(InvoiceLineQuantityMapper.class);
            MockedStatic<LineTaxFactory> mockedLineTaxFactory = Mockito.mockStatic(LineTaxFactory.class);
            MockedStatic<ProductMapper> mockedProductMapper = Mockito.mockStatic(ProductMapper.class);
            MockedStatic<PercentDiscountFactory> mockedPercentDiscountFactory = Mockito.mockStatic(PercentDiscountFactory.class);
        ){
            mockedInvoiceLineQuantityMapper.when(() -> InvoiceLineQuantityMapper.map(any())).thenReturn(expectedInvoiceLineQuantity);
            mockedLineTaxFactory.when(() -> LineTaxFactory.create(any(), any())).thenReturn(expectedLineTax);
            mockedProductMapper.when(() -> ProductMapper.map(any())).thenReturn(expectedProduct);
            mockedPercentDiscountFactory.when(PercentDiscountFactory::create).thenReturn(expectedPercentDiscount);

            InvoiceLine gotInvoiceLine = InvoiceLineMapper.map(invoiceLineNumber, allegroLineItem, invoiceLineMoneyStats);

            //then
            assertNotNull(gotInvoiceLine);
            assertNotNull(gotInvoiceLine.getUnitPrice());
            assertEquals(invoiceLineNumber, gotInvoiceLine.getLineNumber());
            assertEquals(expectedInvoiceLineQuantity, gotInvoiceLine.getQuantity());
            assertEquals(expectedProduct, gotInvoiceLine.getProduct());
            assertEquals(expectedPercentDiscount, gotInvoiceLine.getPercentDiscount());
            assertEquals(expectedLineTax, gotInvoiceLine.getLineTax());
            assertEquals(invoiceLineMoneyStats.totalPriceWithTax(), gotInvoiceLine.getLineTotal());
            assertEquals(invoiceLineMoneyStats.unitPriceWithoutTax(), gotInvoiceLine.getUnitPrice().getUnitPrice());
            assertEquals(allegroOffer.getName(), gotInvoiceLine.getInvoiceLineInformation());

            mockedInvoiceLineQuantityMapper.verify(() -> InvoiceLineQuantityMapper.map(allegroLineItem));
            mockedLineTaxFactory.verify(() -> LineTaxFactory.create(invoiceLineMoneyStats.totalTaxValue(), TaxRateCodeMapping.H));
            mockedProductMapper.verify(() -> ProductMapper.map(allegroOffer));
            mockedPercentDiscountFactory.verify(PercentDiscountFactory::create);
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
        "36.90, 2, 23.00, 30.00, 36.90, 60.00, 73.80, 13.80",  // Mouse x 2 -> for 1 netto 30 PLN, brutto 36,9 PLN, tax 6,9 PLN
        "10.00, 1,  8.00,  9.26, 10.00,  9.26, 10.00,  0.74",  // Transport x 1 netto 9,26 PLN, brutto 10 PLN, tax 0,74 PLN
        "20.00, 4,  0.00, 20.00, 20.00, 80.00, 80.00,  0.00",  // Item x 4 -> for 1 netto 20 PLN, brutto 20 PLN, tax 0 PLN
    })
    public void shouldGetInvoiceItemMoneyStats(
        BigDecimal unitPriceWithTax,
        Integer quantity,
        BigDecimal taxRatePercentage,
        BigDecimal expectedUnitPriceWithoutTax,
        BigDecimal expectedUnitPriceWithTax,
        BigDecimal expectedTotalPriceWithoutTax,
        BigDecimal expectedTotalPriceWithTax,
        BigDecimal expectedTax
    ){

        //given
        Offer allegroOffer = Offer.builder()
            .name("Offer 123")
            .build();

        LineItem allegroLineItem = LineItem.builder()
            .quantity(quantity)
            .offer(allegroOffer)
            .originalPrice(new Cost(unitPriceWithTax, Currency.PLN))
            .price(new Cost(unitPriceWithTax, Currency.PLN))
            .tax(new Tax(taxRatePercentage, "", ""))
            .build();

        //when
        InvoiceLineMoneyStats gotInvoiceLineMoneyStats = InvoiceLineMapper.getInvoiceItemMoneyStats(allegroLineItem);

        //then
        assertNotNull(gotInvoiceLineMoneyStats);
        assertBigDecimalEquals(taxRatePercentage, gotInvoiceLineMoneyStats.taxRatePercentage());
        assertBigDecimalEquals(expectedUnitPriceWithoutTax, gotInvoiceLineMoneyStats.unitPriceWithoutTax());
        assertBigDecimalEquals(expectedUnitPriceWithTax, gotInvoiceLineMoneyStats.unitPriceWithTax());
        assertBigDecimalEquals(expectedTotalPriceWithoutTax, gotInvoiceLineMoneyStats.totalPriceWithoutTax());
        assertBigDecimalEquals(expectedTotalPriceWithTax, gotInvoiceLineMoneyStats.totalPriceWithTax());
        assertBigDecimalEquals(expectedTax, gotInvoiceLineMoneyStats.totalTaxValue());
    }

    private static void assertBigDecimalEquals(BigDecimal expected, BigDecimal actual){

        assertTrue(
            expected.compareTo(actual) == 0,
            () -> "Expected: " + expected + ", but was: " + actual
        );
    }
}