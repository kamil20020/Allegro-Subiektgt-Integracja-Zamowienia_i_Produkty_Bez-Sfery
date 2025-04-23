package pl.kamil_dywan.subiektgt.generated.invoice_line;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamil_dywan.subiektgt.generated.TaxRate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "taxRate",
        "taxValue"
})
public class LineTax {

    @XmlElement(name = "TaxRate", required = true)
    protected TaxRate taxRate;

    @XmlElement(name = "TaxValue", required = true)
    protected String taxValue;

}
