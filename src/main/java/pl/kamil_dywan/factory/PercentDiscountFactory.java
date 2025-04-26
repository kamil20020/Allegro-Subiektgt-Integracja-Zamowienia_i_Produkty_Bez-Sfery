package pl.kamil_dywan.factory;

import pl.kamil_dywan.external.subiektgt.generated.Type;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.PercentDiscount;
import pl.kamil_dywan.external.subiektgt.own.Code;

import java.math.BigDecimal;

public class PercentDiscountFactory {

    private PercentDiscountFactory(){


    }

    public static PercentDiscount create(){

        return new PercentDiscount(
            new Type("", Code.LID),
            BigDecimal.ZERO
        );
    }
}
