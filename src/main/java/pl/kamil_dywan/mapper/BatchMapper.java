package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.factory.BatchTrailerFactory;
import pl.kamil_dywan.external.subiektgt.generated.Batch;
import pl.kamil_dywan.external.subiektgt.generated.BatchTrailer;
import pl.kamil_dywan.external.subiektgt.generated.Invoice;
import pl.kamil_dywan.external.subiektgt.own.Code;
import pl.kamil_dywan.external.subiektgt.own.invoice.DocType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BatchMapper {

    private BatchMapper(){


    }

    public static Batch map(String supplierName, List<Order> allegroOrders){

        List<Invoice> invoices = allegroOrders.stream()
            .map(InvoiceMapper::map)
            .collect(Collectors.toList());

        BatchTrailer batchTrailer = BatchTrailerFactory.create(Code.PLN);

        return Batch.builder()
            .date(LocalDate.now())
            .number(1)
            .supplierName(supplierName)
            .docType(DocType.INVOICE)
            .invoices(invoices)
            .batchTrailer(batchTrailer)
            .build();
    }
}
