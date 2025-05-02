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
import pl.kamil_dywan.service.*;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {

    private static LoginApi loginApi;
    private static OrderApi orderApi;
    private static ProductApi productApi;
    private static AuthService authService;
    private static OrderService orderService;
    private static ProductService productService;

    public static void main(String[] args) {

        AppProperties.loadProperties();
        SecureStorage.load();

        loginApi = new LoginApi();
        orderApi = new OrderApi();
        productApi = new ProductApi();

        BearerAuthApi.init(loginApi::refreshAccessToken);

        authService = new AuthService(loginApi);
        orderService = new OrderService(orderApi);
        productService = new ProductService(productApi);

        Scanner scanner = new Scanner(System.in);

        if(!authService.isUserLogged()){

            GenerateDeviceCodeResponse deviceCodeResponse = authService.generateDeviceCodeAndVerification();

            String verificationUrlComplete = deviceCodeResponse.getVerificationUriComplete();
            String deviceCode = deviceCodeResponse.getDeviceCode();

            System.out.println(verificationUrlComplete);

            scanner.nextLine();

            authService.login(deviceCode);
        }

        OfferProductResponse offerProductResponse = productService.getGeneralProductsPage(0, 10);

        List<Long> offersProductsIds = offerProductResponse.getOffersProducts().stream()
            .map(offerProduct -> offerProduct.getId())
            .collect(Collectors.toList());

        List<ProductOffer> gotProductsOffers = productService.getDetailedProductsByIds(offersProductsIds);
        productService.writeProductsToFile(gotProductsOffers, "./products.epp", ProductType.GOODS);

        OrderResponse orderResponse = orderService.getPage(0, 10);

        orderService.writeOrdersToFile(orderResponse.getOrders(), "./orders.epp");

        System.out.println(productService.getDetailedProductsByIds(offersProductsIds));
        System.out.println(orderService.getPage(0, 10));

        ProductOffer deliveryService = productService.getDeliveryService();
        productService.writeProductsToFile(List.of(deliveryService), "./delivery.epp", ProductType.SERVICES);
    }

}
