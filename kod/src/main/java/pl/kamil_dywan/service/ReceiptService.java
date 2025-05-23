package pl.kamil_dywan.service;

import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.generated.order_item.OrderItem;
import pl.kamil_dywan.external.allegro.own.order.OrderItemMoneyStats;
import pl.kamil_dywan.external.allegro.own.order.OrderMoneyStats;
import pl.kamil_dywan.external.subiektgt.own.product.ProductType;
import pl.kamil_dywan.external.subiektgt.own.receipt.ReceiptHeader;
import pl.kamil_dywan.external.subiektgt.own.receipt.ReceiptItem;
import pl.kamil_dywan.file.EppGroupSpecialType;
import pl.kamil_dywan.file.write.EppFileWriter;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.mapper.receipt.ReceiptHeaderMapper;
import pl.kamil_dywan.mapper.receipt.ReceiptItemMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ReceiptService {

    private static final List<String> lastHeaders;

    static {

        lastHeaders = List.of(
            "DATYZAKONCZENIA", "NUMERYIDENTYFIKACYJNENABYWCOW", "PRZYCZYNYKOREKT", "DOKUMENTYFISKALNEVAT",
            "OPLATYDODATKOWE", "WYMAGALNOSCMPP", "OPLATACUKROWA", "DOKUMENTYZNACZNIKIJPKVAT",
            "INFORMACJEWSTO", "DATYUJECIAKOREKT"
        );
    }

    private static final Integer RECEIPT_HEADER_REAL_LENGTH = 62;
    private static final Integer RECEIPT_CONTENT_REAL_LENGTH = 22;

    private FileWriter<Object> loadFileWriter(int numberOfReceipts){

        List<String> headersNames = new ArrayList<>();
        List<Integer> toWriteHeadersIndexes = new ArrayList<>();
        List<Integer> rowsLengths = new ArrayList<>();
        LinkedHashMap<String, Integer[]> writeIndexes = new LinkedHashMap<>();

        writeIndexes.put(
            EppGroupSpecialType.EMPTY_CONTENT.toString(),
            new Integer[]{0, 1, 2, 3, 6, 18, 19, 21, 22, 24, 25, 26, 27, 28, 29, 30, 32, 34, 35, 36, 37, 38, 39, 40, 44, 45, 46, 47, 52, 53, 54, 56, 58, 61}
        );

        writeIndexes.put(
            EppGroupSpecialType.EMPTY_HEADER.toString(),
            new Integer[]{0, 1, 2, 9, 10, 12, 13, 14, 15, 16, 17, 18, 19}
        );

        for(int i = 0; i < numberOfReceipts; i += 2){

            headersNames.add(EppGroupSpecialType.EMPTY_CONTENT.toString());
            headersNames.add(EppGroupSpecialType.EMPTY_HEADER.toString());

            toWriteHeadersIndexes.add(i);
            toWriteHeadersIndexes.add(i + 1);

            rowsLengths.add(RECEIPT_HEADER_REAL_LENGTH);
            rowsLengths.add(RECEIPT_CONTENT_REAL_LENGTH);
        }

        EppFileWriter<Object> subiektParentFileWriter = new EppFileWriter<>(headersNames, toWriteHeadersIndexes, rowsLengths, writeIndexes);

        subiektParentFileWriter.appendHeaderNames(ProductService.getFileWriter());

        headersNames.addAll(lastHeaders);

        return subiektParentFileWriter;
    }

    public void writeReceiptsToFile(List<Order> allegroOrders, String savedFilePath){

        FileWriter<Object> subiektReceiptFileWriter = loadFileWriter(allegroOrders.size());

        List<Object> receiptData = new ArrayList<>();

        for(Order order : allegroOrders){

            OrderMoneyStats orderMoneyStats = OrderMoneyStats.getSummary(order);
            OffsetDateTime paymentDate = order.getPayment().getFinishedAt();
            ReceiptHeader receiptHeader = ReceiptHeaderMapper.map(orderMoneyStats.orderTotalMoneyStats(), paymentDate);

            List<ReceiptItem> receiptItems = getOrderReceiptItems(order);

            receiptData.add(receiptHeader);
            receiptData.add(receiptItems);
        }

        try {
            subiektReceiptFileWriter.save(savedFilePath, receiptData);
        }
        catch (IOException | URISyntaxException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }
    }

    private List<ReceiptItem> getOrderReceiptItems(Order order){

        List<ReceiptItem> receiptItems = new ArrayList<>();

        int i = 1;

        for(OrderItem orderItem : order.getOrderItems()){

            OrderItemMoneyStats orderItemMoneyStats = OrderItemMoneyStats.getSummary(orderItem);
            ReceiptItem receiptItem = ReceiptItemMapper.map(orderItem, orderItemMoneyStats, i);

            receiptItems.add(receiptItem);

            i++;
        }

        handleDelivery(order, receiptItems);

        return receiptItems;
    }

    private void handleDelivery(Order order, List<ReceiptItem> receiptItems){

        if(order.hasDelivery()){

            int lastReceiptItemIndex = receiptItems.size() - 1;
            ReceiptItem deliveryReceiptItem = receiptItems.get(lastReceiptItemIndex);

            deliveryReceiptItem.setProductType(ProductType.SERVICES);
            deliveryReceiptItem.setId("DOSTAWA123");
        }
    }

}
