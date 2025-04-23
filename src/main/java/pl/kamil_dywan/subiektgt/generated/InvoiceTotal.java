package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {
        "numberOfLines",
        "numberOfTaxRates",
        "lineValueTotal",
        "taxableTotal",
        "taxTotal",
        "netPaymentTotal",
        "grossPaymentTotal"

    }
)
@XmlRootElement(name = "InvoiceTotal")
public class InvoiceTotal {

    @XmlElement(name = "NumberOfLines")
    protected Integer numberOfLines;

    @XmlElement(name = "NumberOfTaxRates")
    protected Integer numberOfTaxRates;

    @XmlElement(name = "LineValueTotal")
    protected BigDecimal lineValueTotal;

    @XmlElement(name = "TaxableTotal")
    protected BigDecimal taxableTotal;

    @XmlElement(name = "TaxTotal", required = true)
    protected BigDecimal taxTotal;

    @XmlElement(name = "NetPaymentTotal", required = true)
    protected BigDecimal netPaymentTotal;

    @XmlElement(name = "GrossPaymentTotal", required = true)
    protected BigDecimal grossPaymentTotal;

}
