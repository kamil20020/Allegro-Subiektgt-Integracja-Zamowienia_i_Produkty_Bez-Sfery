package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.invoice_item.Offer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLineQuantity;
import pl.kamil_dywan.external.subiektgt.own.UOMCode;

import java.math.BigDecimal;

public class InvoiceLineQuantityMapper {

    private InvoiceLineQuantityMapper(){


    }

    public static InvoiceLineQuantity map(LineItem allegroLineItem){

        BigDecimal quantityValue = BigDecimal.valueOf(allegroLineItem.getQuantity());
        Integer packSize = allegroLineItem.getQuantity();

        return InvoiceLineQuantity.builder()
                .packsize(packSize)
                .amount(quantityValue.intValue())
                .uomCode(UOMCode.UNIT)
                .build();
    }
}
