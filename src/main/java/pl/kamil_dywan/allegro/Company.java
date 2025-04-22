package pl.kamil_dywan.allegro;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "ids",
        "vatPayerStatus",
        "taxId"
})
@Generated("jsonschema2pojo")
public class Company {

    @JsonProperty("name")
    private String name;

    @JsonProperty("ids")
    private List<Object> ids = new ArrayList<Object>();

    @JsonProperty("vatPayerStatus")
    private String vatPayerStatus;

    @JsonProperty("taxId")
    private String taxId;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }


    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }


    @JsonProperty("ids")
    public List<Object> getIds() {
        return ids;
    }


    @JsonProperty("ids")
    public void setIds(List<Object> ids) {
        this.ids = ids;
    }


    @JsonProperty("vatPayerStatus")
    public String getVatPayerStatus() {
        return vatPayerStatus;
    }


    @JsonProperty("vatPayerStatus")
    public void setVatPayerStatus(String vatPayerStatus) {
        this.vatPayerStatus = vatPayerStatus;
    }


    @JsonProperty("taxId")
    public String getTaxId() {
        return taxId;
    }


    @JsonProperty("taxId")
    public void setTaxId(String taxId) {
        this.taxId = taxId;
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
        sb.append(Company.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("ids");
        sb.append('=');
        sb.append(((this.ids == null) ? "<null>" : this.ids));
        sb.append(',');
        sb.append("vatPayerStatus");
        sb.append('=');
        sb.append(((this.vatPayerStatus == null) ? "<null>" : this.vatPayerStatus));
        sb.append(',');
        sb.append("taxId");
        sb.append('=');
        sb.append(((this.taxId == null) ? "<null>" : this.taxId));
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
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((result * 31) + ((this.ids == null) ? 0 : this.ids.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.vatPayerStatus == null) ? 0 : this.vatPayerStatus.hashCode()));
        result = ((result * 31) + ((this.taxId == null) ? 0 : this.taxId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Company) == false) {
            return false;
        }
        Company rhs = ((Company) other);
        return ((((((this.name == rhs.name) || ((this.name != null) && this.name.equals(rhs.name))) && ((this.ids == rhs.ids) || ((this.ids != null) && this.ids.equals(rhs.ids)))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties)))) && ((this.vatPayerStatus == rhs.vatPayerStatus) || ((this.vatPayerStatus != null) && this.vatPayerStatus.equals(rhs.vatPayerStatus)))) && ((this.taxId == rhs.taxId) || ((this.taxId != null) && this.taxId.equals(rhs.taxId))));
    }

}
