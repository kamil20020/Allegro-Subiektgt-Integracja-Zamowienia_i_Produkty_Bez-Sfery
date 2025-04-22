package pl.kamil_dywan.allegro;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private Boolean required;

    @JsonProperty("address")
    private Address__3 address;

    @JsonProperty("dueDate")
    private String dueDate;

    @JsonProperty("features")
    private List<Object> features = new ArrayList<Object>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("required")
    public Boolean getRequired() {
        return required;
    }


    @JsonProperty("required")
    public void setRequired(Boolean required) {
        this.required = required;
    }


    @JsonProperty("address")
    public Address__3 getAddress() {
        return address;
    }


    @JsonProperty("address")
    public void setAddress(Address__3 address) {
        this.address = address;
    }


    @JsonProperty("dueDate")
    public String getDueDate() {
        return dueDate;
    }


    @JsonProperty("dueDate")
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


    @JsonProperty("features")
    public List<Object> getFeatures() {
        return features;
    }


    @JsonProperty("features")
    public void setFeatures(List<Object> features) {
        this.features = features;
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
        sb.append(Invoice.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("required");
        sb.append('=');
        sb.append(((this.required == null) ? "<null>" : this.required));
        sb.append(',');
        sb.append("address");
        sb.append('=');
        sb.append(((this.address == null) ? "<null>" : this.address));
        sb.append(',');
        sb.append("dueDate");
        sb.append('=');
        sb.append(((this.dueDate == null) ? "<null>" : this.dueDate));
        sb.append(',');
        sb.append("features");
        sb.append('=');
        sb.append(((this.features == null) ? "<null>" : this.features));
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
        result = ((result * 31) + ((this.features == null) ? 0 : this.features.hashCode()));
        result = ((result * 31) + ((this.address == null) ? 0 : this.address.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.required == null) ? 0 : this.required.hashCode()));
        result = ((result * 31) + ((this.dueDate == null) ? 0 : this.dueDate.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Invoice) == false) {
            return false;
        }
        Invoice rhs = ((Invoice) other);
        return ((((((this.features == rhs.features) || ((this.features != null) && this.features.equals(rhs.features))) && ((this.address == rhs.address) || ((this.address != null) && this.address.equals(rhs.address)))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties)))) && ((this.required == rhs.required) || ((this.required != null) && this.required.equals(rhs.required)))) && ((this.dueDate == rhs.dueDate) || ((this.dueDate != null) && this.dueDate.equals(rhs.dueDate))));
    }

}
