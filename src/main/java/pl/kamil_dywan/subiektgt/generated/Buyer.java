package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "buyerReferences",
        "party",
        "address",
        "contact"
})
@XmlRootElement(name = "Buyer")
public class Buyer {

    @XmlElement(name = "BuyerReferences", required = true)
    protected Buyer.BuyerReferences buyerReferences;

    @XmlElement(name = "Party", required = true)
    protected String party;

    @XmlElement(name = "Address", required = true)
    protected Buyer.Address address;

    @XmlElement(name = "Contact", required = true)
    protected Buyer.Contact contact;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "street",
            "city",
            "postCode"
    })
    public static class Address {

        @XmlElement(name = "Street", required = true)
        protected String street;
        @XmlElement(name = "City", required = true)
        protected String city;
        @XmlElement(name = "PostCode", required = true)
        protected String postCode;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "suppliersCodeForBuyer",
            "taxNumber"
    })
    public static class BuyerReferences {

        @XmlElement(name = "SuppliersCodeForBuyer", required = true)
        protected String suppliersCodeForBuyer;

        @XmlElement(name = "TaxNumber")
        protected long taxNumber;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "name",
            "switchboard",
            "fax"
    })
    public static class Contact {

        @XmlElement(name = "Name", required = true)
        protected String name;

        @XmlElement(name = "Switchboard", required = true)
        protected String switchboard;

        @XmlElement(name = "Fax", required = true)
        protected String fax;

    }
}
