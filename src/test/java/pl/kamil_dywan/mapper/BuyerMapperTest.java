package pl.kamil_dywan.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kamil_dywan.external.allegro.generated.buyer.Buyer;
import pl.kamil_dywan.external.allegro.generated.buyer.BuyerAddress;
import pl.kamil_dywan.external.subiektgt.generated.Address;
import pl.kamil_dywan.external.subiektgt.generated.Contact;

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
            var mockedContactMapper = Mockito.mockStatic(ContactMapper.class)
        ){

            mockedAddressMapper.when(() -> AddressMapper.map(any(BuyerAddress.class))).thenReturn(new Address());
            mockedContactMapper.when(() -> ContactMapper.map(any(Buyer.class))).thenReturn(new Contact());

            pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer gotBuyer = BuyerMapper.map(allegroBuyer);

            //then
            assertEquals(allegroBuyer.getCompanyName(), gotBuyer.getParty());
            assertNotNull(gotBuyer.getAddress());
            assertNotNull(gotBuyer.getContact());

            mockedAddressMapper.verify(() -> AddressMapper.map(allegroBuyerAddress));
            mockedContactMapper.verify(() -> ContactMapper.map(allegroBuyer));
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

        //when
        try(
            MockedStatic<AddressMapper> mockedAddressMapper = Mockito.mockStatic(AddressMapper.class);
            MockedStatic<ContactMapper> mockedContactMapper = Mockito.mockStatic(ContactMapper.class);
        ){

            mockedAddressMapper.when(() -> AddressMapper.map(any(BuyerAddress.class))).thenReturn(new Address());
            mockedContactMapper.when(() -> ContactMapper.map(any(Buyer.class))).thenReturn(new Contact());

            pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer gotBuyer = BuyerMapper.map(allegroBuyer);

            //then
            assertEquals(allegroBuyer.getFirstName() + " " + allegroBuyer.getLastName(), gotBuyer.getParty());
            assertNotNull(gotBuyer.getAddress());
            assertNotNull(gotBuyer.getContact());

            mockedAddressMapper.verify(() -> AddressMapper.map(allegroBuyerAddress));
            mockedContactMapper.verify(() -> ContactMapper.map(allegroBuyer));
        }
    }
}