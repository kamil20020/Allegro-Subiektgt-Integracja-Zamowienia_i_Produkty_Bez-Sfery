package pl.kamil_dywan.subiektgt.generated.buyer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
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
    "suppliersCodeForBuyer",
    "taxNumber"
})
public class BuyerReferences {

    @XmlElement(name = "SuppliersCodeForBuyer", required = true)
    protected String suppliersCodeForBuyer;

    @XmlElement(name = "TaxNumber")
    protected long taxNumber;

}
