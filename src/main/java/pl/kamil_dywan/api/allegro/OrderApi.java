package pl.kamil_dywan.api.allegro;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.BearerAuthApi;
import pl.kamil_dywan.exception.UnloggedException;
import pl.kamil_dywan.external.allegro.own.OrderStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OrderApi extends BearerAuthApi {

    public OrderApi(){

        super("api", "/order/checkout-forms");
    }

    public HttpResponse<String> getOrders(int offset, int limit) throws IllegalStateException, UnloggedException {

        String offsetStr = String.valueOf(offset);
        String limitStr = String.valueOf(limit);

        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
            .GET()
            .uri(
                URI.create(API_PREFIX + getQueryParamsPostFix(
                    "offset", offsetStr,
                    "limit", limitStr,
                    "status", OrderStatus.READY_FOR_PROCESSING.toString())
                )
            )
            .header("Accept", "application/vnd.allegro.public.v1+json");

        return send(httpRequestBuilder);
    }
}
