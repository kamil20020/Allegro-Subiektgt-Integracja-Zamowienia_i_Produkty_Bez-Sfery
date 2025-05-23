package pl.kamil_dywan.external.subiektgt.own.receipt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.kamil_dywan.external.subiektgt.own.invoice.DocType;
import pl.kamil_dywan.file.EppSerializable;

public class ReceiptHeader extends EppSerializable {

    private DocType docType;

    public ReceiptHeader(String... args) {

        super(args);

        this.docType = DocType.valueOf(args[0]);
    }

    public ReceiptHeader(DocType docType) {

        super(null);

        this.docType = docType;
    }

    public ReceiptHeader(){

        super(null);
    }
}