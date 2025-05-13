package pl.kamil_dywan.mapper.integration;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceBatch;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;
import pl.kamil_dywan.file.read.XMLFileReader;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.file.write.XMLFileWriter;
import pl.kamil_dywan.mapper.invoice.InvoiceBatchMapper;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceMapperTestIT {

    FileReader<OrderResponse> allegroOrderReader = new JSONFileReader<>(OrderResponse.class);
    FileReader<InvoiceBatch> subiektOrderReader = new XMLFileReader<>(InvoiceBatch.class);
    FileWriter<InvoiceBatch> subiektOrderWriter = new XMLFileWriter<>(InvoiceBatch.class);

    @Test
    void shouldMap() throws Exception {

        //given
        OrderResponse allegroOrderResponse = allegroOrderReader.load("data/allegro/real-order-1.json");
        InvoiceBatch expectedSubiektBatch = subiektOrderReader.load("data/subiekt/order-for-allegro-minimalized.xml");
        String expectedSubiektBatchStr = subiektOrderWriter.writeToStr(expectedSubiektBatch).replaceAll("\\s", "");

        //when
        InvoiceBatch convertedInvoiceBatch = InvoiceBatchMapper.map("Firma przykładowa systemu InsERT GT", allegroOrderResponse.getOrders());
        String convertedBatchStr = subiektOrderWriter.writeToStr(convertedInvoiceBatch).replaceAll("\\s", "");

        //then
        assertNotNull(convertedInvoiceBatch);
        assertEquals(expectedSubiektBatchStr, convertedBatchStr);
    }
}