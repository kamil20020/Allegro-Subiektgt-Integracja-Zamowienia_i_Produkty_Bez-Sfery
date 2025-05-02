package pl.kamil_dywan.mapper.integration;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.Payment;
import pl.kamil_dywan.external.allegro.generated.buyer.BuyerAddress;
import pl.kamil_dywan.external.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceCompany;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceNaturalPerson;
import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.Address;
import pl.kamil_dywan.external.subiektgt.generated.Batch;
import pl.kamil_dywan.external.subiektgt.generated.Contact;
import pl.kamil_dywan.external.subiektgt.generated.Invoice;
import pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLine;
import pl.kamil_dywan.external.subiektgt.generated.supplier.Supplier;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;
import pl.kamil_dywan.file.read.XMLFileReader;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.file.write.XMLFileWriter;
import pl.kamil_dywan.mapper.BatchMapper;
import pl.kamil_dywan.mapper.InvoiceMapper;

import javax.sql.rowset.spi.XmlWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceMapperTestIT {

    FileReader<OrderResponse> allegroOrderReader = new JSONFileReader<>(OrderResponse.class);
    FileReader<Batch> subiektOrderReader = new XMLFileReader<>(Batch.class);
    FileWriter<Batch> subiektOrderWriter = new XMLFileWriter<>(Batch.class);

    @Test
    void shouldMap() throws Exception {

        //given
        OrderResponse allegroOrderResponse = allegroOrderReader.load("data/allegro/real-order-1.json");
        Batch expectedSubiektBatch = subiektOrderReader.load("data/subiekt/order-for-allegro-minimalized.xml");
        String expectedSubiektBatchStr = subiektOrderWriter.writeToStr(expectedSubiektBatch).replaceAll("\\s", "");

        //when
        Batch convertedBatch = BatchMapper.map("Firma przykładowa systemu InsERT GT", allegroOrderResponse.getOrders());
        String convertedBatchStr = subiektOrderWriter.writeToStr(convertedBatch).replaceAll("\\s", "");

        //then
        assertNotNull(convertedBatch);
        assertEquals(expectedSubiektBatchStr, convertedBatchStr);
    }
}