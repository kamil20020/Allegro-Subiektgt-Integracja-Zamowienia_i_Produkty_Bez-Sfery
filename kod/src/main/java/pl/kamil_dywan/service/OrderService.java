package pl.kamil_dywan.service;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.allegro.OrderApi;
import pl.kamil_dywan.api.allegro.request.CreateOrderInvoiceRequest;
import pl.kamil_dywan.api.allegro.request.CreateOrderInvoiceFile;
import pl.kamil_dywan.api.allegro.response.DocumentIdResponse;
import pl.kamil_dywan.exception.UnloggedException;
import pl.kamil_dywan.api.allegro.response.OrderResponse;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class OrderService {

    private final OrderApi orderApi;

    public OrderService(OrderApi orderApi){

        this.orderApi = orderApi;
    }

    public OrderResponse getPage(int offset, int limit) throws UnloggedException {

        HttpResponse<String> gotResponse = orderApi.getOrders(offset, limit);

        OrderResponse gotOrderResponse = Api.extractBody(gotResponse, OrderResponse.class);

        gotOrderResponse.getOrders()
            .forEach(order -> order.addDeliveryToOrderItems());

        return gotOrderResponse;
    }

    private String createDocument(String orderId) throws UnloggedException, IllegalStateException{

        String documentName = "Dokument_sprzedazy.pdf";
        String invoiceNumber = UUID.randomUUID().toString();

        CreateOrderInvoiceFile createOrderInvoiceFile = new CreateOrderInvoiceFile(documentName);

        CreateOrderInvoiceRequest createOrderInvoiceRequest = new CreateOrderInvoiceRequest(createOrderInvoiceFile, invoiceNumber);

        HttpResponse<String> gotResponse = orderApi.createDocument(orderId, createOrderInvoiceRequest);

        if(gotResponse.statusCode() != 201){

            throw new IllegalStateException(gotResponse.body());
        }

        DocumentIdResponse documentIdResponse = Api.extractBody(gotResponse, DocumentIdResponse.class);

        return documentIdResponse.getId();
    }

    private void saveDocument(String orderId, String documentId, byte[] data) throws UnloggedException, IllegalStateException{

        HttpResponse<String> gotResponse = orderApi.saveDocument(orderId, documentId, data);

        if(gotResponse.statusCode() != 200){

            throw new IllegalStateException(gotResponse.body());
        }
    }

    public void uploadDocument(String orderId, File documentFile) throws UnloggedException, IOException, IllegalStateException {

        Path filePath = documentFile.toPath();
        byte[] fileData = Files.readAllBytes(filePath);

        String createdDocumentId = createDocument(orderId);

        saveDocument(orderId, createdDocumentId, fileData);
    }
}
