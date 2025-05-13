package pl.kamil_dywan.external.subiektgt.own.receipt;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.kamil_dywan.file.EppSerializable;


public class Receipt extends EppSerializable {

    public Receipt(String[] args) {
        super(args);
    }
}
