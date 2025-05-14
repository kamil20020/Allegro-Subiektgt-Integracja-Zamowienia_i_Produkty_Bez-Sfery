package pl.kamil_dywan.service;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.allegro.OrderApi;
import pl.kamil_dywan.exception.UnloggedException;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceBatch;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.file.write.XMLFileWriter;
import pl.kamil_dywan.mapper.invoice.InvoiceBatchMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

public class OrderService {

    private final OrderApi orderApi;
    private static final FileWriter<InvoiceBatch> subiektOrderFileWriter = new XMLFileWriter<>(InvoiceBatch.class);

    public OrderService(OrderApi orderApi){

        this.orderApi = orderApi;
    }

    public OrderResponse getPage(int offset, int limit) throws UnloggedException {

        HttpResponse<String> gotResponse = orderApi.getOrders(offset, limit);

        return Api.extractBody(gotResponse, OrderResponse.class);
    }

    public void writeInvoicesToFile(List<Order> allegroOrders, String filePath) throws IllegalStateException {

        InvoiceBatch invoicesBatch = InvoiceBatchMapper.map("Subiekt", allegroOrders);

        try {
            subiektOrderFileWriter.save(filePath, invoicesBatch);
        }
        catch (IOException | URISyntaxException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }
    }
}
