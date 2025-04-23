package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "taxRate",
    "taxableValueAtRate",
    "taxAtRate",
    "netPaymentAtRate",
    "grossPaymentAtRate",
    "taxCurrency"
})
@XmlRootElement(name = "TaxSubTotal")
public class TaxSubTotal {

    @XmlElement(name = "TaxRate", required = true)
    protected TaxRate taxRate;

    @XmlElement(name = "TaxableValueAtRate", required = true)
    protected String taxableValueAtRate;

    @XmlElement(name = "TaxAtRate", required = true)
    protected String taxAtRate;

    @XmlElement(name = "NetPaymentAtRate", required = true)
    protected String netPaymentAtRate;

    @XmlElement(name = "GrossPaymentAtRate", required = true)
    protected String grossPaymentAtRate;

    @XmlElement(name = "TaxCurrency", required = true)
    protected String taxCurrency;

    @XmlAttribute(name = "Code")
    protected String code;

}
