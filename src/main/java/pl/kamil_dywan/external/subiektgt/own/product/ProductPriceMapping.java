package pl.kamil_dywan.external.subiektgt.own.product;

import lombok.ToString;
import pl.kamil_dywan.file.EppSerializable;

import java.math.BigDecimal;
import java.util.List;


@ToString
public class ProductPriceMapping extends EppSerializable{

    private String productId;
    private String groupName;
    private BigDecimal unitPriceWithoutTax;
    private BigDecimal unitPriceWithTax;
    private BigDecimal markup;
    private BigDecimal margin;
    private BigDecimal profit;

    public ProductPriceMapping(String... args){

        super(args);

        productId = args[0];
        groupName = args[1];
        unitPriceWithoutTax = new BigDecimal(args[2]);
        unitPriceWithTax = new BigDecimal(args[3]);
        markup = new BigDecimal(args[4]);
        margin = new BigDecimal(args[5]);
        profit = new BigDecimal(args[6]);
    }
}
