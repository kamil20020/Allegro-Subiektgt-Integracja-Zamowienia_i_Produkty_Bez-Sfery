package pl.kamil_dywan.subiektgt.generated.settlement;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamil_dywan.subiektgt.own.Code;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"value"}
)
public class SettlementTerms {

    @XmlValue
    @XmlSchemaType(name = "date")
    protected LocalDate value;

    @XmlAttribute(name = "Code")
    protected Code code;

}