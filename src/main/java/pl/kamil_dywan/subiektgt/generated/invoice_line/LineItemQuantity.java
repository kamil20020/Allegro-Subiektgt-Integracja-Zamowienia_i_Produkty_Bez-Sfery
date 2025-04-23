package pl.kamil_dywan.subiektgt.generated.invoice_line;

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
        "packsize",
        "amount"
})
public class LineItemQuantity {

    @XmlElement(name = "Packsize")
    protected byte packsize;

    @XmlElement(name = "Amount")
    protected byte amount;

    @XmlAttribute(name = "UOMCode")
    protected String uomCode;

}
