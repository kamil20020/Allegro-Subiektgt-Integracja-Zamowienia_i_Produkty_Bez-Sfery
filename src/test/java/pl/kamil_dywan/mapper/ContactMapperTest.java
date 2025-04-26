package pl.kamil_dywan.mapper;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.buyer.Buyer;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceNaturalPerson;
import pl.kamil_dywan.external.subiektgt.generated.Contact;

import static org.junit.jupiter.api.Assertions.*;

class ContactMapperTest {

    @Test
    void shouldMapForBuyer() {

        //given
        Buyer buyer = Buyer.builder()
            .firstName("Kamil")
            .lastName("Nowak")
            .phoneNumber("+48 123 456 789")
            .build();

        //when
        Contact gotContact = ContactMapper.map(buyer);

        //then
        assertEquals(buyer.getFirstName() + " " + buyer.getLastName(), gotContact.getName());
        assertEquals(buyer.getPhoneNumber(), gotContact.getSwitchboard());
    }

    @Test
    void shouldMapForNotNullInvoiceNaturalPerson() {

        //given
        InvoiceNaturalPerson invoiceNaturalPerson = InvoiceNaturalPerson.builder()
            .firstName("Kamil")
            .lastName("Nowak")
            .build();

        //when
        Contact gotContact = ContactMapper.map(invoiceNaturalPerson);

        //then
        assertEquals(invoiceNaturalPerson.getFirstName() + " " + invoiceNaturalPerson.getLastName(), gotContact.getName());
        assertEquals("", gotContact.getSwitchboard());
    }

    @Test
    void shouldMapForNullInvoiceNaturalPerson(){

        //given
        InvoiceNaturalPerson invoiceNaturalPerson = null;

        //when
        Contact gotContact = ContactMapper.map(invoiceNaturalPerson);

        //then
        assertNull(gotContact);
    }
}