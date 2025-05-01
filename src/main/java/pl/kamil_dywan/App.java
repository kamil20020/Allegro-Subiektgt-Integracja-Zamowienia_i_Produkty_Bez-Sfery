package pl.kamil_dywan;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.BearerAuthApi;
import pl.kamil_dywan.api.allegro.LoginApi;
import pl.kamil_dywan.api.allegro.OrderApi;
import pl.kamil_dywan.api.allegro.ProductApi;
import pl.kamil_dywan.external.allegro.generated.auth.AccessTokenResponse;
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
import pl.kamil_dywan.service.AppProperties;
import pl.kamil_dywan.service.SecureStorage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        AppProperties.loadProperties();

        SecureStorage.load();

        FileWriter<Batch> subiektBatchWriter = new XMLFileWriter<>(Batch.class);
        FileReader<Batch> subiektBatchReader = new XMLFileReader<>(Batch.class);
        FileWriter<OrderResponse> allegroOrderWriter = new JSONFileWriter<>();
        FileReader<OrderResponse> allegroOrderReader = new JSONFileReader<>(OrderResponse.class);

        OrderResponse allegroOrderResponse = allegroOrderReader.load("data/allegro/real-order-1.json");
        allegroOrderWriter.save("order-output.json", allegroOrderResponse);

        Batch batch = BatchMapper.map("Firma przykładowa systemu InsERT GT", allegroOrderResponse.getOrders());

        subiektBatchWriter.save("./subiekt.xml", batch);

        Batch batch1 = subiektBatchReader.load("data/subiekt/order.xml");

        subiektBatchWriter.save("./subiekt-output.xml", batch1);

        LinkedHashMap<String, Class<? extends EppSerializable>> schema = new LinkedHashMap<>();
        schema.put("TOWARY", Product.class);
        schema.put("CENNIK", ProductPriceMapping.class);

        LinkedHashMap<String, Integer[]> readIndexes = new LinkedHashMap<>();
        readIndexes.put("TOWARY", new Integer[]{0, 1, 4, 11, 14});

        FileReader<ProductRelatedData> eppFileReader = new EppFileReader<>(schema, readIndexes, ProductRelatedData.class);

        ProductRelatedData productRelatedData = eppFileReader.load("data/subiekt/product.epp");
        System.out.println(productRelatedData);

        List<String> headersNames = List.of("TOWARY", "CENNIK", "GRUPYTOWAROW", "CECHYTOWAROW", "DODATKOWETOWAROW", "TOWARYKODYCN", "TOWARYGRUPYJPKVAT");
        List<Integer> toWriteHeadersIndexes = List.of(0, 1);
        List<Integer> rowsLengths = List.of(43, 7);
        LinkedHashMap<String, Integer[]> writeIndexes = new LinkedHashMap<>();
        writeIndexes.put("TOWARY", new Integer[]{0, 1, 4, 11, 14});

        FileWriter<ProductRelatedData> eppFileWriter = new EppFileWriter<>(headersNames, toWriteHeadersIndexes, rowsLengths, writeIndexes);
        eppFileWriter.save("./product-output.epp", productRelatedData);

        LoginApi loginApi = new LoginApi();

        var gotResponse = loginApi.generateDeviceCodeAndVerification();

        GenerateDeviceCodeResponse gotBody = Api.extractBody(gotResponse, GenerateDeviceCodeResponse.class);

        System.out.println(gotBody.getVerificationUriComplete());
        System.out.println(gotBody.getDeviceCode());

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();

        var gotResponse1 = loginApi.generateAccessToken(gotBody.getDeviceCode());
        AccessTokenResponse gotBody1 = Api.extractBody(gotResponse1, AccessTokenResponse.class);

        BearerAuthApi.init(gotBody1.getAccessToken(), gotBody1.getRefreshToken(), loginApi::refreshAccessToken);

        ProductApi productApi = new ProductApi();

        var gotResponse2 = productApi.getOffersProducts(0, 10);

        OfferProductResponse gotBody2 = Api.extractBody(gotResponse2, OfferProductResponse.class);

        System.out.println(gotBody2);

        OrderApi orderApi = new OrderApi();

        var gotResponse3 = orderApi.getOrders(0, 10);

        OrderResponse gotBody3 = Api.extractBody(gotResponse3, OrderResponse.class);

        System.out.println(gotBody3);

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(gotBody1).length());

        SecureStorage.saveCredentials("access_token", gotBody1.getAccessToken());
        SecureStorage.saveCredentials("refresh_token", gotBody1.getRefreshToken());

        System.out.println(SecureStorage.getCredentialsPassword("ACCESS_TOKEN"));
        System.out.println(SecureStorage.getCredentialsPassword("REFRESH_TOKEN"));
    }

}
