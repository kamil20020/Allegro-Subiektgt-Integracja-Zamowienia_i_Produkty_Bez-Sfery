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
@XmlType(name = "", propOrder = {"schema", "parameters", "invoiceType", "function", "invoiceCurrency", "checksum"})
@XmlRootElement(name = "InvoiceHead")
public class InvoiceHead {

    @XmlElement(name = "Schema", required = true)
    protected InvoiceHead.Schema schema;

    @XmlElement(name = "Parameters", required = true)
    protected InvoiceHead.Parameters parameters;

    @XmlElement(name = "InvoiceType", required = true)
    protected InvoiceHead.InvoiceType invoiceType;

    @XmlElement(name = "Function", required = true)
    protected InvoiceHead.Function function;

    @XmlElement(name = "InvoiceCurrency", required = true)
    protected InvoiceHead.InvoiceCurrency invoiceCurrency;

    @XmlElement(name = "Checksum")
    protected int checksum;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"value"})
    public static class Function {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "Code")
        protected String code;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"currency"})
    public static class InvoiceCurrency {

        @XmlElement(name = "Currency", required = true)
        protected InvoiceHead.InvoiceCurrency.Currency currency;

        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"value"})
        public static class Currency {

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
    @XmlType(name = "", propOrder = {"value"})
    public static class InvoiceType {

        @XmlValue
        protected String value;

        @XmlAttribute(name = "Code")
        protected String code;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"language", "decimalSeparator", "precision"})
    public static class Parameters {

        @XmlElement(name = "Language", required = true)
        protected String language;

        @XmlElement(name = "DecimalSeparator", required = true)
        protected String decimalSeparator;

        @XmlElement(name = "Precision")
        protected float precision;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"version"})
    public static class Schema {

        @XmlElement(name = "Version")
        protected byte version;

    }
}
