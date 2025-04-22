package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "settlementTerms"
})
@XmlRootElement(name = "Settlement")
public class Settlement {

    @XmlElement(name = "SettlementTerms", required = true)
    protected Settlement.SettlementTerms settlementTerms;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class SettlementTerms {

        @XmlValue
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar value;

        @XmlAttribute(name = "Code")
        protected String code;

    }
}
