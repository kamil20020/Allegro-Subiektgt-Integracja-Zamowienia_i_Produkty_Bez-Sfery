package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.*;
import pl.kamil_dywan.subiektgt.own.Code;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {
        "taxRate",
        "taxableValueAtRate",
        "taxAtRate",
        "netPaymentAtRate",
        "grossPaymentAtRate",
        "taxCurrency"
    }
)
@XmlRootElement(name = "TaxSubTotal")
public class TaxSubTotal {

    @XmlElement(name = "TaxRate", required = true)
    protected TaxRate taxRate;

    @XmlElement(name = "TaxableValueAtRate", required = true)
    protected BigDecimal taxableValueAtRate;

    @XmlElement(name = "TaxAtRate", required = true)
    protected BigDecimal taxAtRate;

    @XmlElement(name = "NetPaymentAtRate", required = true)
    protected BigDecimal netPaymentAtRate;

    @XmlElement(name = "GrossPaymentAtRate", required = true)
    protected BigDecimal grossPaymentAtRate;

    @XmlElement(name = "TaxCurrency", required = true)
    protected String taxCurrency;

    @XmlAttribute(name = "Code")
    protected Code code;

}
