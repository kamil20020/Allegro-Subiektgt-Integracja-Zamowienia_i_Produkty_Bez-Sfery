package pl.kamil_dywan;

import pl.kamil_dywan.api.BasicAuthApi;
import pl.kamil_dywan.api.BearerAuthApi;
import pl.kamil_dywan.api.allegro.LoginApi;
import pl.kamil_dywan.api.allegro.OrderApi;
import pl.kamil_dywan.api.allegro.ProductApi;
import pl.kamil_dywan.gui.MainGui;
import pl.kamil_dywan.service.*;

import java.util.ArrayList;

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
        BasicAuthApi.init();

        AuthService authService = new AuthService(loginApi);
        authService.init();

        OrderService orderService = new OrderService(orderApi);
        ProductService productService = new ProductService(productApi);
        InvoiceService invoiceService = new InvoiceService();
        ReceiptService receiptService = new ReceiptService();

//        new MainGui(authService, productService, orderService, invoiceService);

        receiptService.writeReceiptsToFile(null);
    }

}
