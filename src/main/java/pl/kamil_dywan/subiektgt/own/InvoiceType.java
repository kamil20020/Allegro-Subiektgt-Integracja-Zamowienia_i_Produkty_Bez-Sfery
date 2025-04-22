package pl.kamil_dywan.subiektgt.own;

public enum InvoiceType {

    VAT("Faktura VAT");

    private String name;

    InvoiceType(String name){

        this.name = name;
    }

    @Override
    public String toString(){

        return name;
    }
}
