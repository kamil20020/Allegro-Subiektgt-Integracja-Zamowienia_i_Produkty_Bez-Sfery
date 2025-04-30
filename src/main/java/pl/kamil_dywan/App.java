package pl.kamil_dywan;

import com.ctc.wstx.shaded.msv.org_isorelax.dispatcher.IslandSchema;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException, URISyntaxException {

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

        List<String> headersNames = List.of("TOWARY, CENNIK");

        FileWriter<ProductRelatedData> eppFileWriter = new EppFileWriter<>(headersNames, new LinkedHashMap<>());
        eppFileWriter.save("./product-output.epp", productRelatedData);
    }

}
