package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "suppliersInvoiceNumber"
})
@XmlRootElement(name = "InvoiceReferences")
public class InvoiceReferences {

    @XmlElement(name = "SuppliersInvoiceNumber", required = true)
    protected String suppliersInvoiceNumber;

}
