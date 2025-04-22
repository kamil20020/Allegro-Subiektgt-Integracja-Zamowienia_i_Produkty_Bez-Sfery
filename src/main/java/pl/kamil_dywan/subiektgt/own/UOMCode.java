package pl.kamil_dywan.subiektgt.own;

public enum UOMCode {

    UNIT("szt.");

    private String value;

    private UOMCode(String value){

        this.value = value;
    }

    @Override
    public String toString(){

        return value;
    }
}
