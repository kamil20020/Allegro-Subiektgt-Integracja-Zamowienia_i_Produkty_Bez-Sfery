package pl.kamil_dywan.allegro.generated.invoice_item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import pl.kamil_dywan.allegro.generated.Cost;

import javax.annotation.processing.Generated;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "type",
    "status",
    "externalTransactionId",
    "amount"
})
@Generated("jsonschema2pojo")
public class Voucher {

    @JsonProperty("code")
    private String code;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    @JsonProperty("externalTransactionId")
    private String externalTransactionId;

    @JsonProperty("amount")
    private Cost amount;

}
