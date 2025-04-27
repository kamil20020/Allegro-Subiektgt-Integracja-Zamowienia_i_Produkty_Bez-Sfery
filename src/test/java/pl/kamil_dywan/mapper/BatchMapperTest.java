package pl.kamil_dywan.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.subiektgt.generated.Batch;
import pl.kamil_dywan.external.subiektgt.generated.BatchTrailer;
import pl.kamil_dywan.external.subiektgt.generated.Invoice;
import pl.kamil_dywan.external.subiektgt.own.Code;
import pl.kamil_dywan.external.subiektgt.own.DocType;
import pl.kamil_dywan.external.subiektgt.own.InvoiceType;
import pl.kamil_dywan.factory.BatchTrailerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class BatchMapperTest {

    @Test
    void shouldMap() {

        //given
        String supplierName = "Supplier 123";

        Order allegroOrder1 = new Order();
        Order allegroOrder2 = new Order();

        List<Order> allegroOrders = List.of(allegroOrder1, allegroOrder2);

        Invoice expectedInvoice1 = new Invoice();
        Invoice expectedInvoice2 = new Invoice();

        List<Invoice> expectedInvoices = List.of(expectedInvoice1, expectedInvoice2);

        BatchTrailer expectedBatchTrailer = new BatchTrailer();

        //when
        try(
            MockedStatic<InvoiceMapper> mockedInvoiceMapper = Mockito.mockStatic(InvoiceMapper.class);
            MockedStatic<BatchTrailerFactory> mockedBatchTrailerFactory = Mockito.mockStatic(BatchTrailerFactory.class);
        ){
            mockedInvoiceMapper.when(() -> InvoiceMapper.map(eq(allegroOrder1))).thenReturn(expectedInvoice1);
            mockedInvoiceMapper.when(() -> InvoiceMapper.map(eq(allegroOrder2))).thenReturn(expectedInvoice2);

            mockedBatchTrailerFactory.when(() -> BatchTrailerFactory.create(any())).thenReturn(expectedBatchTrailer);

            Batch gotBatch = BatchMapper.map(supplierName, allegroOrders);

            //then
            assertNotNull(gotBatch);
            assertNotNull(gotBatch.getDate());
            assertNotNull(gotBatch.getInvoices());
            assertNotNull(gotBatch.getBatchTrailer());

            assertEquals(supplierName, gotBatch.getSupplierName());
            assertEquals(1, gotBatch.getNumber());
            assertEquals(DocType.INVOICE, gotBatch.getDocType());
            assertTrue(gotBatch.getInvoices().containsAll(expectedInvoices));
            assertEquals(expectedBatchTrailer, gotBatch.getBatchTrailer());

            mockedInvoiceMapper.verify(() -> InvoiceMapper.map(allegroOrder1), Mockito.times(2));
            mockedInvoiceMapper.verify(() -> InvoiceMapper.map(allegroOrder2), Mockito.times(2));

            mockedBatchTrailerFactory.verify(() -> BatchTrailerFactory.create(Code.PLN));
        }
    }
}