package pl.kamil_dywan.service;

import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceBatch;
import pl.kamil_dywan.external.subiektgt.own.product.ProductRelatedData;
import pl.kamil_dywan.external.subiektgt.own.receipt.Receipt;
import pl.kamil_dywan.file.write.EppFileWriter;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.file.write.XMLFileWriter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ReceiptService {

    static {

        EppFileWriter<ProductRelatedData> subiektParentFileWriter = new EppFileWriter<>();

        subiektParentFileWriter.append(ProductService.getFileWriter(), 1);

        subiektParentFileWriter.append(
            List.of(
                "DATYZAKONCZENIA", "NUMERYIDENTYFIKACYJNENABYWCOW", "PRZYCZYNYKOREKT", "DOKUMENTYFISKALNEVAT",
                "OPLATYDODATKOWE", "WYMAGALNOSCMPP", "OPLATACUKROWA", "DOKUMENTYZNACZNIKIJPKVAT",
                "INFORMACJEWSTO", "DATYUJECIAKOREKT"
            )
        );

        subiektReceiptFileWriter = subiektParentFileWriter;
    }

    private static final FileWriter<ProductRelatedData> subiektReceiptFileWriter;

    public void writeReceiptsToFile(List<Order> allegroOrders){

        List<Receipt> receipts = new ArrayList<>();


    }
}
