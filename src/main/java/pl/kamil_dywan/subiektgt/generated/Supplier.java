package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "supplierReferences",
        "party",
        "address",
        "contact"
})
@XmlRootElement(name = "Supplier")
public class Supplier {

    @XmlElement(name = "SupplierReferences", required = true)
    protected Supplier.SupplierReferences supplierReferences;

    @XmlElement(name = "Party", required = true)
    protected String party;

    @XmlElement(name = "Address", required = true)
    protected Supplier.Address address;

    @XmlElement(name = "Contact", required = true)
    protected Supplier.Contact contact;

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

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "buyersCodeForSupplier",
            "taxNumber"
    })
    public static class SupplierReferences {

        @XmlElement(name = "BuyersCodeForSupplier", required = true)
        protected String buyersCodeForSupplier;

        @XmlElement(name = "TaxNumber", required = true)
        protected String taxNumber;

    }
}
