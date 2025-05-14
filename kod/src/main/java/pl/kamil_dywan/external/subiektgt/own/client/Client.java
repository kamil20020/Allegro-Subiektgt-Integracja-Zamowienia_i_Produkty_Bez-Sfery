package pl.kamil_dywan.external.subiektgt.own.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.kamil_dywan.external.subiektgt.own.product.ProductType;
import pl.kamil_dywan.file.EppSerializable;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
public class Client extends EppSerializable {

    private ClientType clientType;
    private String id;
    private String shortName;
    private String longName;
    private String city;
    private String postCode;
    private String street;
    private String nip;
    private String countryCode;

    public Client(String... args) {
        super(args);

        this.clientType = ClientType.valueOf(args[0]);
        this.id = args[1];
        this.shortName = args[2];
        this.longName = args[3];
        this.city = args[4];
        this.postCode = args[5];
        this.street = args[6];
        this.nip = args[7];
        this.countryCode = args[8];
    }

    public Client(ClientType clientType, String id, String shortName, String longName, String city, String postCode, String street, String nip, String countryCode) {

        super(null);

        this.clientType = clientType;
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
        this.nip = nip;
        this.countryCode = countryCode;
    }

    public Client(){

        super(null);
    }
}
