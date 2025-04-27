package pl.kamil_dywan.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kamil_dywan.external.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceCompany;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceNaturalPerson;
import pl.kamil_dywan.external.subiektgt.generated.Address;
import pl.kamil_dywan.external.subiektgt.generated.Contact;
import pl.kamil_dywan.external.subiektgt.generated.supplier.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class SupplierMapperTest {

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

        Address expectedAddress = new Address();
        Contact expectedContact = new Contact();

        //when
        try(
            MockedStatic<AddressMapper> mockedAddressMapper = Mockito.mockStatic(AddressMapper.class);
            MockedStatic<ContactMapper> mockedContactMapper = Mockito.mockStatic(ContactMapper.class)
        ){
            mockedAddressMapper.when(() -> AddressMapper.map(any(InvoiceAddress.class))).thenReturn(expectedAddress);
            mockedContactMapper.when(() -> ContactMapper.map(any(InvoiceNaturalPerson.class))).thenReturn(expectedContact);

            Supplier gotSupplier = SupplierMapper.map(allegroInvoice);

            //then
            assertNotNull(gotSupplier);
            assertEquals(expectedAddress, gotSupplier.getAddress());
            assertEquals(expectedContact, gotSupplier.getContact());

            assertEquals(allegroInvoiceCompany.getName(), gotSupplier.getParty());

            mockedAddressMapper.verify(() -> AddressMapper.map(allegroInvoiceAddress));
            mockedContactMapper.verify(() -> ContactMapper.map(invoiceNaturalPerson));
        }
    }
}