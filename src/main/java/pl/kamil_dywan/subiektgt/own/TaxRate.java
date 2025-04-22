package pl.kamil_dywan.subiektgt.own;

public enum TaxRate{

    L(Code.L, 8),
    H(Code.H, 23),
    Z(Code.Z, 0);

    private Code code;
    private Integer value;

    private TaxRate(Code code, Integer value){

        this.code = code;
        this.value = value;
    }

    public static TaxRate getByValue(Integer value){

        return switch(value){

            case 8 -> TaxRate.L;
            case 23 -> TaxRate.H;
            case 0 -> TaxRate.Z;
            default -> throw new IllegalArgumentException("Tax rate of subiekt was not found");
        };
    }

    public Code getCode(){

        return code;
    }

    public Integer getValue(){

        return value;
    }
}
