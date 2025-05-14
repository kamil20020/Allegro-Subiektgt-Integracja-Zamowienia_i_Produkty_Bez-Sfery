package pl.kamil_dywan.service;

import pl.kamil_dywan.external.subiektgt.own.client.Client;
import pl.kamil_dywan.external.subiektgt.own.client.Clients;
import pl.kamil_dywan.external.subiektgt.own.product.ProductRelatedData;
import pl.kamil_dywan.file.write.EppFileWriter;
import pl.kamil_dywan.file.write.FileWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;

public class ClientService {

    private static FileWriter<Clients> subiektClientFileWriter;

    static {

        List<String> headersNames = List.of("KONTRAHENCI", "GRUPYKONTRAHENTOW", "CECHYKONTRAHENTOW", "DODATKOWEKONTRAHENTOW");
        List<Integer> toWriteHeadersIndexes = List.of(0);
        List<Integer> rowsLengths = List.of(31);
        LinkedHashMap<String, Integer[]> writeIndexes = new LinkedHashMap<>();
        writeIndexes.put("KONTRAHENCI", new Integer[]{0, 1, 2, 3, 4, 5, 6, 28});

        subiektClientFileWriter = new EppFileWriter<>(headersNames, toWriteHeadersIndexes, rowsLengths, writeIndexes);
    }

    public void writeClientsToFile(List<Client> clientsList, String filePath){

        Clients clients = new Clients(clientsList);

        try{
            subiektClientFileWriter.save(filePath, clients);
        }
        catch (IOException | URISyntaxException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }
    }

}
