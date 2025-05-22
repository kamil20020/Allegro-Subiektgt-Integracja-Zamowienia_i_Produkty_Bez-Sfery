package pl.kamil_dywan.service;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.allegro.OrderApi;
import pl.kamil_dywan.exception.UnloggedException;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;

import java.net.http.HttpResponse;

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
}
