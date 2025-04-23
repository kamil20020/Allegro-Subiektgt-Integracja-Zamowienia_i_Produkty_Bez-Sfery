package pl.kamil_dywan.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamil_dywan.subiektgt.generated.ItemCurrency;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "itemCurrency",
        "checksum"
})
@XmlRootElement(name = "BatchTrailer")
public class BatchTrailer {

    @XmlElement(name = "ItemCurrency", required = true)
    protected ItemCurrency itemCurrency;

    @XmlElement(name = "Checksum", required = true)
    protected String checksum;

}
