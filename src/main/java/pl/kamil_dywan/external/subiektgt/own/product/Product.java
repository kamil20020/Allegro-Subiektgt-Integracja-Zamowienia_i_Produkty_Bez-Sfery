package pl.kamil_dywan.external.subiektgt.own.product;

import lombok.ToString;
import pl.kamil_dywan.file.EppSerializable;

import java.math.BigDecimal;

@ToString
public class Product extends EppSerializable {

    private String id;
    private Integer type;
    private String name;
    private TaxRateCodeMapping taxRateCodeMapping;
    private BigDecimal unitPriceWithoutTax;

    public Product(String... args){

        super(args);

        id = args[1];
        type = Integer.valueOf(args[0]);
        name = args[2];
        taxRateCodeMapping = TaxRateCodeMapping.getByValue(new BigDecimal(args[3]));
        unitPriceWithoutTax = new BigDecimal(args[4]);
    }
}
