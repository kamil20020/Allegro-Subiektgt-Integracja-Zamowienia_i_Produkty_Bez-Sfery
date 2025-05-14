package pl.kamil_dywan.mapper.invoice;

import pl.kamil_dywan.external.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceCompany;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceNaturalPerson;
import pl.kamil_dywan.external.subiektgt.generated.Address;
import pl.kamil_dywan.external.subiektgt.generated.Contact;
import pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer;
import pl.kamil_dywan.external.subiektgt.generated.buyer.BuyerReferences;
import pl.kamil_dywan.mapper.AddressMapper;

public interface BuyerMapper {

    static Buyer map(InvoiceAddress allegroInvoiceAddress){

        if(allegroInvoiceAddress == null){
            return null;
        }

        InvoiceCompany allegroInvoiceCompany = allegroInvoiceAddress.getCompany();

        if(allegroInvoiceCompany == null){
            return mapForPrivatePerson(allegroInvoiceAddress);
        }

        return mapForCompany(allegroInvoiceAddress);
    }

    private static Buyer mapForPrivatePerson(InvoiceAddress allegroInvoiceAddress){

        InvoiceNaturalPerson allegroInvoiceNaturalPerson = allegroInvoiceAddress.getNaturalPerson();

        String party = allegroInvoiceNaturalPerson.simpleToString();

        Address address = AddressMapper.map(allegroInvoiceAddress);
        Contact contact = ContactMapper.map(allegroInvoiceNaturalPerson);

        BuyerReferences buyerReferences = new BuyerReferences();

        buyerReferences.setSuppliersCodeForBuyer(allegroInvoiceNaturalPerson.simpleToString());

        return Buyer.builder()
            .party(party)
            .buyerReferences(buyerReferences)
            .address(address)
            .contact(contact)
            .build();
    }

    private static Buyer mapForCompany(InvoiceAddress allegroInvoiceAddress){

        InvoiceCompany allegroInvoiceCompany = allegroInvoiceAddress.getCompany();
        InvoiceNaturalPerson allegroInvoiceNaturalPerson = allegroInvoiceAddress.getNaturalPerson();

        BuyerReferences buyerReferences = new BuyerReferences();

        buyerReferences.setSuppliersCodeForBuyer(allegroInvoiceCompany.getName());

        String nip = allegroInvoiceCompany.getTaxId();

        if(nip != null){

            buyerReferences.setTaxNumber(nip);
        }

        Address address = AddressMapper.map(allegroInvoiceAddress);
        Contact contact = ContactMapper.map(allegroInvoiceNaturalPerson);

        return Buyer.builder()
            .party(allegroInvoiceCompany.getName())
            .buyerReferences(buyerReferences)
            .address(address)
            .contact(contact)
            .build();
    }
}
