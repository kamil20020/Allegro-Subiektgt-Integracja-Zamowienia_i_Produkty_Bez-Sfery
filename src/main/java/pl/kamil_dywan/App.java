package pl.kamil_dywan;

import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.Batch;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;
import pl.kamil_dywan.file.read.XMLFileReader;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.file.write.JSONFileWriter;
import pl.kamil_dywan.file.write.XMLFileWriter;
import pl.kamil_dywan.mapper.*;

import java.io.IOException;
import java.net.URISyntaxException;

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
    }

}
