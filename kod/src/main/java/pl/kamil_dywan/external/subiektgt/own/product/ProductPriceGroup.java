package pl.kamil_dywan.external.subiektgt.own.product;

public enum ProductPriceGroup {

    RETAIL("Detaliczna"),
    WHOLESALE("Hurtowa"),
    SPECIAL("Specjalna");

    private String name;

    private ProductPriceGroup(String name){

        this.name = name;
    }

    @Override
    public String toString(){

        return name;
    }
}
