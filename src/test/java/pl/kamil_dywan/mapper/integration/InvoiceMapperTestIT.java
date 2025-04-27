package pl.kamil_dywan.mapper.integration;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.Batch;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.XMLFileReader;
import pl.kamil_dywan.mapper.BatchMapper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceMapperTestIT {

    FileReader<Batch> subiektBatchReader = new XMLFileReader<>(Batch.class);

    @Test
    void shouldMap() throws URISyntaxException, IOException {

        //given
        Batch gotBatch = subiektBatchReader.load("data/subiekt/real-order-1.json");

        //when

        //then
    }
}