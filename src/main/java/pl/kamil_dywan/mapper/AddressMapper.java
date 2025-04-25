package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.buyer.BuyerAddress;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.subiektgt.generated.Address;

public class AddressMapper {

    private AddressMapper(){


    }

    public static Address map(BuyerAddress buyerAddress){

        return Address.builder()
            .city(buyerAddress.getCity())
            .street(buyerAddress.getStreet())
            .postCode(buyerAddress.getPostCode())
            .build();
    }

    public static Address map(InvoiceAddress invoiceAddress){

        return Address.builder()
            .city(invoiceAddress.getCity())
            .street(invoiceAddress.getStreet())
            .postCode(invoiceAddress.getZipCode())
            .build();
    }
}
