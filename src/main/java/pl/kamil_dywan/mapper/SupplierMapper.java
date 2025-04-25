package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.subiektgt.generated.Address;
import pl.kamil_dywan.external.subiektgt.generated.Contact;
import pl.kamil_dywan.external.subiektgt.generated.supplier.Supplier;

public class SupplierMapper {

    private SupplierMapper(){


    }

    public static Supplier map(Invoice allegroInvoice){

        InvoiceAddress allegroInvoiceAddress = allegroInvoice.getAddress();

        Address address = AddressMapper.map(allegroInvoiceAddress);
        Contact contact = ContactMapper.map(allegroInvoiceAddress.getNaturalPerson());

        return Supplier.builder()
            .party(allegroInvoiceAddress.getCompany().getName())
            .address(address)
            .contact(contact)
            .build();
    }
}
