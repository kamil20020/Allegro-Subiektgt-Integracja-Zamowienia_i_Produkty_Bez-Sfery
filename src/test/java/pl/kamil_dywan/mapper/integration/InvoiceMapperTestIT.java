package pl.kamil_dywan.mapper.integration;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.Batch;
import pl.kamil_dywan.external.subiektgt.generated.Invoice;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;
import pl.kamil_dywan.file.read.XMLFileReader;
import pl.kamil_dywan.mapper.BatchMapper;
import pl.kamil_dywan.mapper.InvoiceMapper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceMapperTestIT {

    FileReader<OrderResponse> allegroOrderReader = new JSONFileReader<>(OrderResponse.class);

    @Test
    void shouldMap() throws URISyntaxException, IOException {

        //given
        OrderResponse allegroOrderResponse = allegroOrderReader.load("data/allegro/real-order-1.json");
        Order allegroOrder = allegroOrderResponse.getOrders().get(0);

        //when
        Invoice gotInvoice = InvoiceMapper.map(allegroOrder);

        //then

    }
}