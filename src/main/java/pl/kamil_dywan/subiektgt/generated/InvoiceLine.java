package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "lineNumber",
        "product",
        "quantity",
        "price",
        "percentDiscount",
        "lineTax",
        "lineTotal",
        "invoiceLineInformation"
})
@XmlRootElement(name = "InvoiceLine")
public class InvoiceLine {

    @XmlElement(name = "LineNumber")
    protected byte lineNumber;

    @XmlElement(name = "Product", required = true)
    protected InvoiceLine.Product product;

    @XmlElement(name = "Quantity", required = true)
    protected InvoiceLine.Quantity quantity;

    @XmlElement(name = "Price", required = true)
    protected InvoiceLine.Price price;

    @XmlElement(name = "PercentDiscount", required = true)
    protected InvoiceLine.PercentDiscount percentDiscount;

    @XmlElement(name = "LineTax", required = true)
    protected InvoiceLine.LineTax lineTax;

    @XmlElement(name = "LineTotal", required = true)
    protected String lineTotal;

    @XmlElement(name = "InvoiceLineInformation", required = true)
    protected String invoiceLineInformation;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "taxRate",
            "taxValue"
    })
    public static class LineTax {

        @XmlElement(name = "TaxRate", required = true)
        protected InvoiceLine.LineTax.TaxRate taxRate;

        @XmlElement(name = "TaxValue", required = true)
        protected String taxValue;

        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class TaxRate {

            @XmlValue
            protected byte value;

            @XmlAttribute(name = "Code")
            protected String code;

        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "type",
            "percentage"
    })
    public static class PercentDiscount {

        @XmlElement(name = "Type", required = true)
        protected InvoiceLine.PercentDiscount.Type type;

        @XmlElement(name = "Percentage")
        protected byte percentage;

        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class Type {

            @XmlValue
            protected String value;

            @XmlAttribute(name = "Code")
            protected String code;

        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "unitPrice"
    })
    public static class Price {

        @XmlElement(name = "UnitPrice", required = true)
        protected String unitPrice;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "suppliersProductCode",
            "description"
    })
    public static class Product {

        @XmlElement(name = "SuppliersProductCode", required = true)
        protected String suppliersProductCode;

        @XmlElement(name = "Description", required = true)
        protected String description;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "packsize",
            "amount"
    })
    public static class Quantity {

        @XmlElement(name = "Packsize")
        protected byte packsize;

        @XmlElement(name = "Amount")
        protected byte amount;

        @XmlAttribute(name = "UOMCode")
        protected String uomCode;

        public String getUOMCode() {
            return uomCode;
        }

        public void setUOMCode(String value) {
            this.uomCode = value;
        }

    }
}
