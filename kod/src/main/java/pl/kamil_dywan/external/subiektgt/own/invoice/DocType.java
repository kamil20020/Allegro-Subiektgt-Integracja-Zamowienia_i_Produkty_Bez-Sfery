package pl.kamil_dywan.external.subiektgt.own.invoice;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum DocType {

    @XmlEnumValue("Invoice") INVOICE("Invoice"),
    @XmlEnumValue("PA") RECEIPT("PA");

    private String name;

    DocType(String name){

        this.name = name;
    }

    @Override
    public String toString(){

        return name;
    }
}
