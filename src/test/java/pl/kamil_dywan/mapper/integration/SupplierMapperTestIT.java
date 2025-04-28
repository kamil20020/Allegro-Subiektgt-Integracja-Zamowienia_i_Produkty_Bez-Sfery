package pl.kamil_dywan.mapper.integration;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceCompany;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceNaturalPerson;

class SupplierMapperTestIT {

    @Test
    void shouldMap() {

        //given
        InvoiceCompany allegroInvoiceCompany = new InvoiceCompany();
        allegroInvoiceCompany.setName("Company 123");

        InvoiceNaturalPerson invoiceNaturalPerson = new InvoiceNaturalPerson("Kamil", "Nowak");

        InvoiceAddress allegroInvoiceAddress = InvoiceAddress.builder()
            .company(allegroInvoiceCompany)
            .naturalPerson(invoiceNaturalPerson)
            .build();

        Invoice allegroInvoice = Invoice.builder()
            .address(allegroInvoiceAddress)
            .build();

        //when
    }
}