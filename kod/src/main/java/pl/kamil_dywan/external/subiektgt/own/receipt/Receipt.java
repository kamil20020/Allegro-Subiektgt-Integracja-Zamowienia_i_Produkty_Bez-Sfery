package pl.kamil_dywan.external.subiektgt.own.receipt;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.kamil_dywan.external.subiektgt.own.invoice.DocType;
import pl.kamil_dywan.file.EppSerializable;

import java.util.List;


public class Receipt extends EppSerializable {

    private ReceiptHeader receiptHeader;
    private List<ReceiptItem> receiptItems;

    public Receipt(String[] args) {
        super(args);
    }
}
