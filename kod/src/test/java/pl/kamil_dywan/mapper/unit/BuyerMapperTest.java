package pl.kamil_dywan.mapper.unit;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kamil_dywan.external.allegro.generated.buyer.Buyer;
import pl.kamil_dywan.external.allegro.generated.buyer.BuyerAddress;
import pl.kamil_dywan.external.subiektgt.generated.Address;
import pl.kamil_dywan.external.subiektgt.generated.Contact;
import pl.kamil_dywan.mapper.AddressMapper;
import pl.kamil_dywan.mapper.invoice.BuyerMapper;
import pl.kamil_dywan.mapper.invoice.ContactMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class BuyerMapperTest {

    @Test
    void shouldMapWithCompanyName() {

        //given
        BuyerAddress allegroBuyerAddress = BuyerAddress.builder()
            .city("City 123")
            .street("Street 123")
            .postCode("12-123")
            .build();

        Buyer allegroBuyer = Buyer.builder()
            .companyName("Company 123")
            .address(allegroBuyerAddress)
            .build();

        //when
        try(
            var mockedAddressMapper = Mockito.mockStatic(AddressMapper.class);
            var mockedContactMapper = Mockito.mockStatic(ContactMapper.class);
        ){

            mockedAddressMapper.when(() -> AddressMapper.map(any(BuyerAddress.class))).thenReturn(new Address());
//            mockedContactMapper.when(() -> ContactMapper.map(any(Buyer.class))).thenReturn(new Contact());

            pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer gotBuyer = BuyerMapper.map(null);

            //then
            assertEquals(allegroBuyer.getCompanyName(), gotBuyer.getParty());
            assertNotNull(gotBuyer.getAddress());
            assertNotNull(gotBuyer.getContact());

            mockedAddressMapper.verify(() -> AddressMapper.map(allegroBuyerAddress));
//            mockedContactMapper.verify(() -> ContactMapper.map(allegroBuyer));
        }
    }

    @Test
    public void shouldMapWithoutCompanyName(){

        //given
        BuyerAddress allegroBuyerAddress = BuyerAddress.builder()
            .street("Street 123")
            .city("City 123")
            .postCode("12-123")
            .build();

        Buyer allegroBuyer = Buyer.builder()
            .firstName("Kamil")
            .lastName("Nowak")
            .address(allegroBuyerAddress)
            .build();

        Address expectedAddress = new Address();
        Contact expectedContact = new Contact();

        //when
        try(
            MockedStatic<AddressMapper> mockedAddressMapper = Mockito.mockStatic(AddressMapper.class);
            MockedStatic<ContactMapper> mockedContactMapper = Mockito.mockStatic(ContactMapper.class);
        ){

            mockedAddressMapper.when(() -> AddressMapper.map(any(BuyerAddress.class))).thenReturn(expectedAddress);
//            mockedContactMapper.when(() -> ContactMapper.map(any(Buyer.class))).thenReturn(expectedContact);

            pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer gotBuyer = BuyerMapper.map(null);

            //then
            assertNotNull(gotBuyer);
            assertEquals(allegroBuyer.getFirstName() + " " + allegroBuyer.getLastName(), gotBuyer.getParty());
            assertEquals(expectedAddress, gotBuyer.getAddress());
            assertEquals(expectedContact, gotBuyer.getContact());

            mockedAddressMapper.verify(() -> AddressMapper.map(allegroBuyerAddress));
//            mockedContactMapper.verify(() -> ContactMapper.map(allegroBuyer));
        }
    }
}