package pl.kamil_dywan.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kamil_dywan.TestUtils;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;
import pl.kamil_dywan.service.InvoiceService;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceServiceTestIT {

    FileReader<OrderResponse> allegroOrderReader = new JSONFileReader<>(OrderResponse.class);

    private static final String validOrdersFilePath = "data/subiekt/order-for-allegro-minimalized.xml";

    private static final Charset charset = Charset.forName("windows-1250");

    private static String expectedOrders = "";

    static {

        try {
            expectedOrders = FileReader.loadStrFromFile(validOrdersFilePath, charset);
            expectedOrders = TestUtils.removeWhiteSpace(expectedOrders);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private InvoiceService invoiceService;

    @BeforeEach
    public void setUp(){

        invoiceService = new InvoiceService();
    }

    @Test
    void shouldWriteInvoicesToFile() throws Exception{

        //given
        OrderResponse allegroOrderResponse = allegroOrderReader.load("data/allegro/order-invoice-company.json");

        String toCreateFilePath = "test-invoice.xml";

        List<Order> expectedAllegroOrders = allegroOrderResponse.getOrders();

        //when
        invoiceService.writeInvoicesToFile(expectedAllegroOrders, toCreateFilePath);

        String gotSavedInvoice = FileReader.loadStrFromFileOutside(toCreateFilePath, charset);
        gotSavedInvoice = TestUtils.removeWhiteSpace(gotSavedInvoice);

        //then
        assertNotNull(gotSavedInvoice);
        assertEquals(expectedOrders, gotSavedInvoice);
    }
}