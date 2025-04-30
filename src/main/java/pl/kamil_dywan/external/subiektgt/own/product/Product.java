package pl.kamil_dywan.external.subiektgt.own.product;

import lombok.ToString;
import pl.kamil_dywan.file.EppSerializable;

import java.math.BigDecimal;

@ToString
public class Product extends EppSerializable {

    private Integer type;
    private String id;
    private String name;
    private BigDecimal taxRatePercentage;
    private BigDecimal unitPriceWithoutTax;

    public Product(String... args){

        super(args);

        type = Integer.valueOf(args[0]);
        id = args[1];
        name = args[2];
        taxRatePercentage = new BigDecimal(args[3]);
        unitPriceWithoutTax = new BigDecimal(args[4]);
    }
}
