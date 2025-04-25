package pl.kamil_dywan.external.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;
import pl.kamil_dywan.external.subiektgt.own.BigDecimalAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    protected Integer numberOfLines = 0;

    @XmlElement(name = "NumberOfTaxRates")
    protected Integer numberOfTaxRates = 0;

    @XmlElement(name = "LineValueTotal")
    @XmlJavaTypeAdapter(value = BigDecimalAdapter.class)
    protected BigDecimal lineValueTotal = BigDecimal.ZERO;

    @XmlElement(name = "TaxableTotal")
    @XmlJavaTypeAdapter(value = BigDecimalAdapter.class)
    protected BigDecimal taxableTotal = BigDecimal.ZERO;

    @XmlElement(name = "TaxTotal", required = true)
    @XmlJavaTypeAdapter(value = BigDecimalAdapter.class)
    protected BigDecimal taxTotal = BigDecimal.ZERO;

    @XmlElement(name = "NetPaymentTotal", required = true)
    @XmlJavaTypeAdapter(value = BigDecimalAdapter.class)
    protected BigDecimal netPaymentTotal = BigDecimal.ZERO;

    @XmlElement(name = "GrossPaymentTotal", required = true)
    @XmlJavaTypeAdapter(value = BigDecimalAdapter.class)
    protected BigDecimal grossPaymentTotal = BigDecimal.ZERO;

    public static InvoiceTotal getEmpty(){

        return InvoiceTotal.builder()
            .numberOfLines(0)
            .numberOfTaxRates(0)
            .lineValueTotal(BigDecimal.ZERO)
            .taxableTotal(BigDecimal.ZERO)
            .taxTotal(BigDecimal.ZERO)
            .netPaymentTotal(BigDecimal.ZERO)
            .grossPaymentTotal(BigDecimal.ZERO)
            .build();
    }

    public void update(BigDecimal lineValueTotal, BigDecimal taxTotal, BigDecimal netPaymentTotal){

        this.lineValueTotal = this.lineValueTotal.add(lineValueTotal);
        this.taxableTotal = this.lineValueTotal;
        this.taxTotal = this.taxTotal.add(taxTotal);
        this.netPaymentTotal = this.netPaymentTotal.add(netPaymentTotal);
        this.grossPaymentTotal = this.netPaymentTotal;
    }

    public void scale(int scale, RoundingMode roundingMode){

        lineValueTotal = lineValueTotal.setScale(scale, roundingMode);
        taxableTotal = taxableTotal.setScale(scale, roundingMode);
        taxTotal = taxTotal.setScale(scale, roundingMode);
        netPaymentTotal = netPaymentTotal.setScale(scale, roundingMode);
        grossPaymentTotal = grossPaymentTotal.setScale(scale, roundingMode);
    }

}
