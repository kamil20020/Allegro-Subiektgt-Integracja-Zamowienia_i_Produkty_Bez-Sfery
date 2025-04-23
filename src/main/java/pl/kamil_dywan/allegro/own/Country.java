package pl.kamil_dywan.allegro.own;

public enum Country {

    PL("PL"),
    CZ("CZ"),
    SK("SK"),
    HU("HU");

    private String name;

    private Country(String name){

        this.name = name;
    }

    @Override
    public String toString(){

        return name;
    }
}
