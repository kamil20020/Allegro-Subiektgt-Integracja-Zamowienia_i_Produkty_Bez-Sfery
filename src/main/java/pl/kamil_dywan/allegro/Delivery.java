package pl.kamil_dywan.allegro;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "address",
        "method",
        "pickupPoint",
        "cost",
        "time",
        "smart",
        "cancellation",
        "calculatedNumberOfPackages"
})
@Generated("jsonschema2pojo")
public class Delivery {

    @JsonProperty("address")
    private Address__1 address;

    @JsonProperty("method")
    private Method method;

    @JsonProperty("pickupPoint")
    private PickupPoint pickupPoint;

    @JsonProperty("cost")
    private Cost cost;

    @JsonProperty("time")
    private Time time;

    @JsonProperty("smart")
    private Boolean smart;

    @JsonProperty("cancellation")
    private Cancellation cancellation;

    @JsonProperty("calculatedNumberOfPackages")
    private Integer calculatedNumberOfPackages;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("address")
    public Address__1 getAddress() {
        return address;
    }


    @JsonProperty("address")
    public void setAddress(Address__1 address) {
        this.address = address;
    }


    @JsonProperty("method")
    public Method getMethod() {
        return method;
    }


    @JsonProperty("method")
    public void setMethod(Method method) {
        this.method = method;
    }


    @JsonProperty("pickupPoint")
    public PickupPoint getPickupPoint() {
        return pickupPoint;
    }


    @JsonProperty("pickupPoint")
    public void setPickupPoint(PickupPoint pickupPoint) {
        this.pickupPoint = pickupPoint;
    }


    @JsonProperty("cost")
    public Cost getCost() {
        return cost;
    }


    @JsonProperty("cost")
    public void setCost(Cost cost) {
        this.cost = cost;
    }


    @JsonProperty("time")
    public Time getTime() {
        return time;
    }


    @JsonProperty("time")
    public void setTime(Time time) {
        this.time = time;
    }


    @JsonProperty("smart")
    public Boolean getSmart() {
        return smart;
    }


    @JsonProperty("smart")
    public void setSmart(Boolean smart) {
        this.smart = smart;
    }


    @JsonProperty("cancellation")
    public Cancellation getCancellation() {
        return cancellation;
    }


    @JsonProperty("cancellation")
    public void setCancellation(Cancellation cancellation) {
        this.cancellation = cancellation;
    }


    @JsonProperty("calculatedNumberOfPackages")
    public Integer getCalculatedNumberOfPackages() {
        return calculatedNumberOfPackages;
    }


    @JsonProperty("calculatedNumberOfPackages")
    public void setCalculatedNumberOfPackages(Integer calculatedNumberOfPackages) {
        this.calculatedNumberOfPackages = calculatedNumberOfPackages;
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
        sb.append(Delivery.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("address");
        sb.append('=');
        sb.append(((this.address == null) ? "<null>" : this.address));
        sb.append(',');
        sb.append("method");
        sb.append('=');
        sb.append(((this.method == null) ? "<null>" : this.method));
        sb.append(',');
        sb.append("pickupPoint");
        sb.append('=');
        sb.append(((this.pickupPoint == null) ? "<null>" : this.pickupPoint));
        sb.append(',');
        sb.append("cost");
        sb.append('=');
        sb.append(((this.cost == null) ? "<null>" : this.cost));
        sb.append(',');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null) ? "<null>" : this.time));
        sb.append(',');
        sb.append("smart");
        sb.append('=');
        sb.append(((this.smart == null) ? "<null>" : this.smart));
        sb.append(',');
        sb.append("cancellation");
        sb.append('=');
        sb.append(((this.cancellation == null) ? "<null>" : this.cancellation));
        sb.append(',');
        sb.append("calculatedNumberOfPackages");
        sb.append('=');
        sb.append(((this.calculatedNumberOfPackages == null) ? "<null>" : this.calculatedNumberOfPackages));
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
        result = ((result * 31) + ((this.pickupPoint == null) ? 0 : this.pickupPoint.hashCode()));
        result = ((result * 31) + ((this.address == null) ? 0 : this.address.hashCode()));
        result = ((result * 31) + ((this.cost == null) ? 0 : this.cost.hashCode()));
        result = ((result * 31) + ((this.cancellation == null) ? 0 : this.cancellation.hashCode()));
        result = ((result * 31) + ((this.method == null) ? 0 : this.method.hashCode()));
        result = ((result * 31) + ((this.calculatedNumberOfPackages == null) ? 0 : this.calculatedNumberOfPackages.hashCode()));
        result = ((result * 31) + ((this.time == null) ? 0 : this.time.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.smart == null) ? 0 : this.smart.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Delivery) == false) {
            return false;
        }
        Delivery rhs = ((Delivery) other);
        return ((((((((((this.pickupPoint == rhs.pickupPoint) || ((this.pickupPoint != null) && this.pickupPoint.equals(rhs.pickupPoint))) && ((this.address == rhs.address) || ((this.address != null) && this.address.equals(rhs.address)))) && ((this.cost == rhs.cost) || ((this.cost != null) && this.cost.equals(rhs.cost)))) && ((this.cancellation == rhs.cancellation) || ((this.cancellation != null) && this.cancellation.equals(rhs.cancellation)))) && ((this.method == rhs.method) || ((this.method != null) && this.method.equals(rhs.method)))) && ((this.calculatedNumberOfPackages == rhs.calculatedNumberOfPackages) || ((this.calculatedNumberOfPackages != null) && this.calculatedNumberOfPackages.equals(rhs.calculatedNumberOfPackages)))) && ((this.time == rhs.time) || ((this.time != null) && this.time.equals(rhs.time)))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties)))) && ((this.smart == rhs.smart) || ((this.smart != null) && this.smart.equals(rhs.smart))));
    }

}
