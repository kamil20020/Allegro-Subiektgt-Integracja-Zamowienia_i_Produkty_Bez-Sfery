package pl.kamil_dywan.external.subiektgt.own.client;

public enum ClientType {

    COMPANY("2"),
    PERSON("9");

    private String value;

    private ClientType(String value){

        this.value = value;
    }

    @Override
    public String toString(){

        return value;
    }
}
