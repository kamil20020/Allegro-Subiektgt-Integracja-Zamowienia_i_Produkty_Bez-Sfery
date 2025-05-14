package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceCompany;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.subiektgt.generated.Address;
import pl.kamil_dywan.external.subiektgt.generated.Contact;
import pl.kamil_dywan.external.subiektgt.own.client.Client;
import pl.kamil_dywan.external.subiektgt.own.client.ClientType;
import pl.kamil_dywan.mapper.invoice.ContactMapper;

public interface ClientMapper {

//    private ClientType clientType;
//    private String id;
//    private String shortName;
//    private String longName;
//    private String city;
//    private String postCode;
//    private String street;
//    private String nip;
//    private String countryCode;

    public static Client map(Order allegroOrder){

        Invoice allegroInvoice = allegroOrder.getInvoice();
        InvoiceAddress allegroInvoiceAddress = allegroInvoice.getAddress();

        String clientName = allegroOrder.getClientName();

        Client.ClientBuilder newClientBuilder = Client.builder()
            .id(clientName)
            .shortName(clientName)
            .longName(clientName);

        if(allegroInvoiceAddress != null){

            newClientBuilder = newClientBuilder
                .city(allegroInvoiceAddress.getCity())
                .postCode(allegroInvoiceAddress.getZipCode())
                .street(allegroInvoiceAddress.getStreet())
                .countryCode(allegroInvoiceAddress.getCountryCode());

            InvoiceCompany allegroInvoiceCompany = allegroInvoiceAddress.getCompany();

            if(allegroInvoiceCompany != null){

                if(allegroInvoiceCompany.getTaxId() != null){

                    newClientBuilder.nip(allegroInvoiceCompany.getTaxId());
                }
            }
        }

        return newClientBuilder
            .clientType(ClientType.COMPANY)
            .build();
    }
}
