package pl.kamil_dywan.service;

import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.subiektgt.own.invoice.DocType;
import pl.kamil_dywan.external.subiektgt.own.product.ProductType;
import pl.kamil_dywan.external.subiektgt.own.receipt.ReceiptHeader;
import pl.kamil_dywan.external.subiektgt.own.receipt.ReceiptItem;
import pl.kamil_dywan.file.EppGroupSpecialType;
import pl.kamil_dywan.file.write.EppFileWriter;
import pl.kamil_dywan.file.write.FileWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ReceiptService {

    static {

        List<String> headersNames = List.of(EppGroupSpecialType.EMPTY_CONTENT.toString(), EppGroupSpecialType.EMPTY_HEADER.toString(), EppGroupSpecialType.EMPTY_CONTENT.toString(), EppGroupSpecialType.EMPTY_HEADER.toString());
        List<Integer> toWriteHeadersIndexes = List.of(0, 1, 2, 3);
        List<Integer> rowsLengths = List.of(1, 1, 1);

        LinkedHashMap<String, Integer[]> writeIndexes = new LinkedHashMap<>();

//        writeIndexes.put(EppGroupSpecialType.EMPTY_HEADER.toString(), new Integer[]{0});

        EppFileWriter<Object> subiektParentFileWriter = new EppFileWriter<>(headersNames, toWriteHeadersIndexes, rowsLengths, writeIndexes);

//        subiektParentFileWriter.append(ProductService.getFileWriter(), 1);

//        subiektParentFileWriter.append(
//            List.of(
//                "DATYZAKONCZENIA", "NUMERYIDENTYFIKACYJNENABYWCOW", "PRZYCZYNYKOREKT", "DOKUMENTYFISKALNEVAT",
//                "OPLATYDODATKOWE", "WYMAGALNOSCMPP", "OPLATACUKROWA", "DOKUMENTYZNACZNIKIJPKVAT",
//                "INFORMACJEWSTO", "DATYUJECIAKOREKT"
//            )
//        );

        subiektReceiptFileWriter = subiektParentFileWriter;
    }

    private static final FileWriter<Object> subiektReceiptFileWriter;

    public void writeReceiptsToFile(List<Order> allegroOrders){

        List<Object> receiptData = new ArrayList<>();

        receiptData.add(new ReceiptHeader(DocType.RECEIPT));
        receiptData.add(List.of(new ReceiptItem(ProductType.GOODS)));

        receiptData.add(new ReceiptHeader(DocType.RECEIPT));
        receiptData.add(List.of(new ReceiptItem(ProductType.GOODS)));

        try {
            subiektReceiptFileWriter.save("test-recipe.epp", receiptData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
