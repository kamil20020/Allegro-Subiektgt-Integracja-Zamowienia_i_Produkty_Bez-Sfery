package pl.kamil_dywan.service;

import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceBatch;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.file.write.XMLFileWriter;
import pl.kamil_dywan.mapper.invoice.InvoiceBatchMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class InvoiceService {

    private static final FileWriter<InvoiceBatch> subiektOrderFileWriter = new XMLFileWriter<>(InvoiceBatch.class);

    public void writeInvoicesToFile(List<Order> allegroOrders, String filePath) throws IllegalStateException {

        InvoiceBatch invoicesBatch = InvoiceBatchMapper.map("Subiekt", allegroOrders);

        try {
            subiektOrderFileWriter.save(filePath, invoicesBatch);
        }
        catch (IOException | URISyntaxException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }
    }
}
