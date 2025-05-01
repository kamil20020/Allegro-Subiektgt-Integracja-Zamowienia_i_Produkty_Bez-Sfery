package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.subiektgt.generated.Address;
import pl.kamil_dywan.external.subiektgt.generated.Contact;
import pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer;

public interface BuyerMapper {

    static Buyer map(pl.kamil_dywan.external.allegro.generated.buyer.Buyer allegroBuyer){

        String party = null;

        if(allegroBuyer.getCompanyName() == null){
            party = allegroBuyer.getFirstName() + " " + allegroBuyer.getLastName();
        }
        else{
            party = allegroBuyer.getCompanyName();
        }

        Address address = AddressMapper.map(allegroBuyer.getAddress());
        Contact contact = ContactMapper.map(allegroBuyer);

        return Buyer.builder()
            .party(party)
            .address(address)
            .contact(contact)
            .build();
    }
}
