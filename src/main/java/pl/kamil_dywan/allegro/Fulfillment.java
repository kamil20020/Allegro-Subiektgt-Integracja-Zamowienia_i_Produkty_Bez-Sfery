package pl.kamil_dywan.allegro;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "shipmentSummary"
})
@Generated("jsonschema2pojo")
public class Fulfillment {

    @JsonProperty("status")
    private String status;

    @JsonProperty("shipmentSummary")
    private ShipmentSummary shipmentSummary;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }


    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }


    @JsonProperty("shipmentSummary")
    public ShipmentSummary getShipmentSummary() {
        return shipmentSummary;
    }


    @JsonProperty("shipmentSummary")
    public void setShipmentSummary(ShipmentSummary shipmentSummary) {
        this.shipmentSummary = shipmentSummary;
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
        sb.append(Fulfillment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null) ? "<null>" : this.status));
        sb.append(',');
        sb.append("shipmentSummary");
        sb.append('=');
        sb.append(((this.shipmentSummary == null) ? "<null>" : this.shipmentSummary));
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
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.shipmentSummary == null) ? 0 : this.shipmentSummary.hashCode()));
        result = ((result * 31) + ((this.status == null) ? 0 : this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Fulfillment) == false) {
            return false;
        }
        Fulfillment rhs = ((Fulfillment) other);
        return ((((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties))) && ((this.shipmentSummary == rhs.shipmentSummary) || ((this.shipmentSummary != null) && this.shipmentSummary.equals(rhs.shipmentSummary)))) && ((this.status == rhs.status) || ((this.status != null) && this.status.equals(rhs.status))));
    }

}
