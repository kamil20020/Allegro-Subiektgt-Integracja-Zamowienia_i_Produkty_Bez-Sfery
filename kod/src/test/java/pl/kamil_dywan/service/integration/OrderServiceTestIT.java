package pl.kamil_dywan.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kamil_dywan.TestUtils;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceBatch;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;
import pl.kamil_dywan.file.read.XMLFileReader;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.file.write.XMLFileWriter;
import pl.kamil_dywan.mapper.invoice.InvoiceBatchMapper;
import pl.kamil_dywan.service.OrderService;
import pl.kamil_dywan.service.ProductService;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTestIT {

    FileReader<OrderResponse> allegroOrderReader = new JSONFileReader<>(OrderResponse.class);
    FileReader<InvoiceBatch> subiektOrderReader = new XMLFileReader<>(InvoiceBatch.class);
    FileWriter<InvoiceBatch> subiektOrderWriter = new XMLFileWriter<>(InvoiceBatch.class);

    private OrderService orderService;

    @BeforeEach
    public void setUp(){

        orderService = new OrderService(null);
    }

    @Test
    void shouldWriteInvoicesToFile() throws Exception{

        //given
        OrderResponse allegroOrderResponse = allegroOrderReader.load("data/allegro/real-order-1.json");
        InvoiceBatch expectedSubiektBatch = subiektOrderReader.load("data/subiekt/order-for-allegro-minimalized.xml");
        String expectedSubiektBatchStr = subiektOrderWriter.writeToStr(expectedSubiektBatch).replaceAll("\\s", "");

        String toCreateFilePath = "test-invoice.xml";

        List<Order> expectedAllegroOrders = allegroOrderResponse.getOrders();

        //when
        orderService.writeInvoicesToFile(expectedAllegroOrders, toCreateFilePath);

        String gotSavedInvoice = FileReader.loadStrFromFileOutside(toCreateFilePath, Charset.forName("windows-1250")).replaceAll("\\s", "");

        //then
        assertNotNull(gotSavedInvoice);
        assertEquals(expectedSubiektBatchStr, gotSavedInvoice);
    }
}