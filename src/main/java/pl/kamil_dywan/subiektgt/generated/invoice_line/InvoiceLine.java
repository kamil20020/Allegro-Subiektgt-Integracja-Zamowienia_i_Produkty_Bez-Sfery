package pl.kamil_dywan.subiektgt.generated.invoice_line;

import jakarta.xml.bind.annotation.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "lineNumber",
        "product",
        "quantity",
        "price",
        "percentDiscount",
        "lineTax",
        "lineTotal",
        "invoiceLineInformation"
})
@XmlRootElement(name = "InvoiceLine")
public class InvoiceLine {

    @XmlElement(name = "LineNumber")
    protected byte lineNumber;

    @XmlElement(name = "Product", required = true)
    protected Product product;

    @XmlElement(name = "Quantity", required = true)
    protected LineItemQuantity quantity;

    @XmlElement(name = "Price", required = true)
    protected UnitPriceHolder price;

    @XmlElement(name = "PercentDiscount", required = true)
    protected PercentDiscount percentDiscount;

    @XmlElement(name = "LineTax", required = true)
    protected LineTax lineTax;

    @XmlElement(name = "LineTotal", required = true)
    protected String lineTotal;

    @XmlElement(name = "InvoiceLineInformation", required = true)
    protected String invoiceLineInformation;

}
