package pl.kamil_dywan.service;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.allegro.OrderApi;
import pl.kamil_dywan.exception.UnloggedException;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.Batch;
import pl.kamil_dywan.external.subiektgt.generated.Invoice;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.file.write.XMLFileWriter;
import pl.kamil_dywan.mapper.BatchMapper;
import pl.kamil_dywan.mapper.InvoiceMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    private final OrderApi orderApi;
    private static final FileWriter<Batch> subiektOrderFileWriter = new XMLFileWriter<>(Batch.class);

    public OrderService(OrderApi orderApi){

        this.orderApi = orderApi;
    }

    public OrderResponse getPage(int offset, int limit) throws UnloggedException {

        HttpResponse<String> gotResponse = orderApi.getOrders(offset, limit);

        return Api.extractBody(gotResponse, OrderResponse.class);
    }

    public void writeOrdersToFile(List<Order> allegroOrders, String filePath) throws IllegalStateException {

        Batch invoicesBatch = BatchMapper.map("Subiekt", allegroOrders);

        try {
            subiektOrderFileWriter.save(filePath, invoicesBatch);
        }
        catch (IOException | URISyntaxException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }
    }
}
