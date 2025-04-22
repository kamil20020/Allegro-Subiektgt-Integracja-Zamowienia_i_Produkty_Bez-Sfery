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
        "itemCurrency",
        "checksum"
})
@XmlRootElement(name = "BatchTrailer")
public class BatchTrailer {

    @XmlElement(name = "ItemCurrency", required = true)
    protected BatchTrailer.ItemCurrency itemCurrency;

    @XmlElement(name = "Checksum", required = true)
    protected String checksum;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "currency"
    })
    public static class ItemCurrency {

        @XmlElement(name = "Currency", required = true)
        protected BatchTrailer.ItemCurrency.Currency currency;

        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class Currency {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "Code")
            protected String code;

        }
    }
}
