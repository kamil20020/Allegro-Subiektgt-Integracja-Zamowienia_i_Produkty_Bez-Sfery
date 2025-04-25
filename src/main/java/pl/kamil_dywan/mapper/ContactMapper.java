package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.buyer.Buyer;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceNaturalPerson;
import pl.kamil_dywan.external.subiektgt.generated.Contact;

public class ContactMapper {

    private ContactMapper(){


    }

    public static Contact map(Buyer allegroBuyer){

        return Contact.builder()
            .name(allegroBuyer.getFirstName() + " " + allegroBuyer.getLastName())
            .switchboard(allegroBuyer.getPhoneNumber())
            .build();
    }

    public static Contact map(InvoiceNaturalPerson invoiceNaturalPerson){

        if(invoiceNaturalPerson == null){
            return null;
        }

        return Contact.builder()
            .name(invoiceNaturalPerson.getFirstName() + " " + invoiceNaturalPerson.getLastName())
            .switchboard("")
            .build();
    }
}
