package pl.kamil_dywan;

import pl.kamil_dywan.api.BearerAuthApi;
import pl.kamil_dywan.api.allegro.LoginApi;
import pl.kamil_dywan.api.allegro.OrderApi;
import pl.kamil_dywan.api.allegro.ProductApi;
import pl.kamil_dywan.external.allegro.generated.auth.GenerateDeviceCodeResponse;
import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProductResponse;
import pl.kamil_dywan.external.allegro.generated.offer_product.ProductOffer;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.own.product.Product;
import pl.kamil_dywan.external.subiektgt.own.product.ProductType;
import pl.kamil_dywan.gui.MainGui;
import pl.kamil_dywan.service.*;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        AppProperties.loadProperties();
        SecureStorage.load();

        LoginApi loginApi = new LoginApi();
        OrderApi orderApi = new OrderApi();
        ProductApi productApi = new ProductApi();

        BearerAuthApi.init(loginApi::refreshAccessToken);

        AuthService authService = new AuthService(loginApi);
        OrderService orderService = new OrderService(orderApi);
        ProductService productService = new ProductService(productApi);

        new MainGui(authService, productService, orderService);
    }

}
