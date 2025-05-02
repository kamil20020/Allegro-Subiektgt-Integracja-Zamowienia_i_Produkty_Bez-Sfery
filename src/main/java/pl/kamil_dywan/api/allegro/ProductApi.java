package pl.kamil_dywan.api.allegro;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.BearerAuthApi;
import pl.kamil_dywan.exception.UnloggedException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProductApi extends BearerAuthApi {

    public ProductApi(){

        super("api", "/sale/offers");
    }

    public HttpResponse<String> getOffersProducts(int offset, int limit) throws IllegalStateException, UnloggedException {

        String offsetStr = String.valueOf(offset);
        String limitStr = String.valueOf(limit);

        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(API_PREFIX + getQueryParamsPostFix("offset", offsetStr, "limit", limitStr)))
            .header("Accept", "application/vnd.allegro.public.v1+json");

        return send(httpRequestBuilder);
    }

    public HttpResponse<String> getProductOfferById(Long id){

        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(API_PREFIX + "/" + id))
            .header("Accept", "application/vnd.allegro.public.v1+json");

        return send(httpRequestBuilder);
    }
}
