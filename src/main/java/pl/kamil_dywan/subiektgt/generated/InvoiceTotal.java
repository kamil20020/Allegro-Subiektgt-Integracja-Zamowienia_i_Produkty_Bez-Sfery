package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "numberOfLines",
        "numberOfTaxRates",
        "lineValueTotal",
        "taxableTotal",
        "taxTotal",
        "netPaymentTotal",
        "grossPaymentTotal"
})
@XmlRootElement(name = "InvoiceTotal")
public class InvoiceTotal {

    @XmlElement(name = "NumberOfLines")
    protected byte numberOfLines;

    @XmlElement(name = "NumberOfTaxRates")
    protected byte numberOfTaxRates;

    @XmlElement(name = "LineValueTotal")
    protected String lineValueTotal;

    @XmlElement(name = "TaxableTotal")
    protected String taxableTotal;

    @XmlElement(name = "TaxTotal", required = true)
    protected String taxTotal;

    @XmlElement(name = "NetPaymentTotal", required = true)
    protected String netPaymentTotal;

    @XmlElement(name = "GrossPaymentTotal", required = true)
    protected String grossPaymentTotal;

}
