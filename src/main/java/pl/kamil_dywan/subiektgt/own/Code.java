package pl.kamil_dywan.subiektgt.own;

public enum Code {

    INVOICE("INV"),
    PLN("PLN"),
    FII("FII"),
    LID("LID"),
    Z("Z"),
    H("H"),
    L("L"),
    Code14I("14I");

    private String name;

    Code(String name){

        this.name = name;
    }

    @Override
    public String toString(){

        return name;
    }
}
