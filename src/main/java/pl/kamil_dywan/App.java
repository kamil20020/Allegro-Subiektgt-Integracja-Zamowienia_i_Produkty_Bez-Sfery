package pl.kamil_dywan;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.BearerAuthApi;
import pl.kamil_dywan.api.allegro.LoginApi;
import pl.kamil_dywan.api.allegro.OrderApi;
import pl.kamil_dywan.api.allegro.ProductApi;
import pl.kamil_dywan.external.allegro.generated.auth.GenerateDeviceCodeResponse;
import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProductResponse;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.Batch;
import pl.kamil_dywan.external.subiektgt.own.product.Product;
import pl.kamil_dywan.external.subiektgt.own.product.ProductPriceMapping;
import pl.kamil_dywan.external.subiektgt.own.product.ProductRelatedData;
import pl.kamil_dywan.file.EppSerializable;
import pl.kamil_dywan.file.read.EppFileReader;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;
import pl.kamil_dywan.file.read.XMLFileReader;
import pl.kamil_dywan.file.write.EppFileWriter;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.file.write.JSONFileWriter;
import pl.kamil_dywan.file.write.XMLFileWriter;
import pl.kamil_dywan.mapper.*;
import pl.kamil_dywan.service.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

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

        System.out.println(productService.getPage(0, 10));
        System.out.println(orderService.getPage(0, 10));
    }

}
