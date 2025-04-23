package pl.kamil_dywan.allegro.generated.invoice;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "required",
    "address",
    "dueDate",
    "features"
})
@Generated("jsonschema2pojo")
public class Invoice {

    @JsonProperty("required")
    private boolean required;

    @JsonProperty("address")
    private InvoiceAddress address;

    @JsonProperty("dueDate")
    private LocalDate dueDate;

    @JsonProperty("features")
    private List<String> features = new ArrayList<>();

}
