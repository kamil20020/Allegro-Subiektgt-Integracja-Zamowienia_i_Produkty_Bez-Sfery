package pl.kamil_dywan.allegro;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "lineItemsSent"
})
@Generated("jsonschema2pojo")
public class ShipmentSummary {

    @JsonProperty("lineItemsSent")
    private String lineItemsSent;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("lineItemsSent")
    public String getLineItemsSent() {
        return lineItemsSent;
    }

    @JsonProperty("lineItemsSent")
    public void setLineItemsSent(String lineItemsSent) {
        this.lineItemsSent = lineItemsSent;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ShipmentSummary.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("lineItemsSent");
        sb.append('=');
        sb.append(((this.lineItemsSent == null) ? "<null>" : this.lineItemsSent));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null) ? "<null>" : this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.lineItemsSent == null) ? 0 : this.lineItemsSent.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ShipmentSummary) == false) {
            return false;
        }
        ShipmentSummary rhs = ((ShipmentSummary) other);
        return (((this.lineItemsSent == rhs.lineItemsSent) || ((this.lineItemsSent != null) && this.lineItemsSent.equals(rhs.lineItemsSent))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
