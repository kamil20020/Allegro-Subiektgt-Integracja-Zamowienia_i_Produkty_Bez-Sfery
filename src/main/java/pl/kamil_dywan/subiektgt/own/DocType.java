package pl.kamil_dywan.subiektgt.own;

public enum DocType {

    INVOICE("Invoice");

    private String name;

    DocType(String name){

        this.name = name;
    }

    @Override
    public String toString(){

        return name;
    }
}
