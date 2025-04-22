package pl.kamil_dywan.allegro;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "type",
        "provider",
        "finishedAt",
        "paidAmount",
        "reconciliation",
        "features"
})
@Generated("jsonschema2pojo")
public class Payment {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("finishedAt")
    private String finishedAt;

    @JsonProperty("paidAmount")
    private PaidAmount paidAmount;

    @JsonProperty("reconciliation")
    private Reconciliation reconciliation;

    @JsonProperty("features")
    private List<Object> features = new ArrayList<Object>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }


    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }


    @JsonProperty("type")
    public String getType() {
        return type;
    }


    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }


    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }


    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }


    @JsonProperty("finishedAt")
    public String getFinishedAt() {
        return finishedAt;
    }


    @JsonProperty("finishedAt")
    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }


    @JsonProperty("paidAmount")
    public PaidAmount getPaidAmount() {
        return paidAmount;
    }


    @JsonProperty("paidAmount")
    public void setPaidAmount(PaidAmount paidAmount) {
        this.paidAmount = paidAmount;
    }


    @JsonProperty("reconciliation")
    public Reconciliation getReconciliation() {
        return reconciliation;
    }


    @JsonProperty("reconciliation")
    public void setReconciliation(Reconciliation reconciliation) {
        this.reconciliation = reconciliation;
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
        sb.append(Payment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null) ? "<null>" : this.type));
        sb.append(',');
        sb.append("provider");
        sb.append('=');
        sb.append(((this.provider == null) ? "<null>" : this.provider));
        sb.append(',');
        sb.append("finishedAt");
        sb.append('=');
        sb.append(((this.finishedAt == null) ? "<null>" : this.finishedAt));
        sb.append(',');
        sb.append("paidAmount");
        sb.append('=');
        sb.append(((this.paidAmount == null) ? "<null>" : this.paidAmount));
        sb.append(',');
        sb.append("reconciliation");
        sb.append('=');
        sb.append(((this.reconciliation == null) ? "<null>" : this.reconciliation));
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
        result = ((result * 31) + ((this.provider == null) ? 0 : this.provider.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((result * 31) + ((this.paidAmount == null) ? 0 : this.paidAmount.hashCode()));
        result = ((result * 31) + ((this.reconciliation == null) ? 0 : this.reconciliation.hashCode()));
        result = ((result * 31) + ((this.finishedAt == null) ? 0 : this.finishedAt.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Payment) == false) {
            return false;
        }
        Payment rhs = ((Payment) other);
        return (((((((((this.features == rhs.features) || ((this.features != null) && this.features.equals(rhs.features))) && ((this.provider == rhs.provider) || ((this.provider != null) && this.provider.equals(rhs.provider)))) && ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id)))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties)))) && ((this.type == rhs.type) || ((this.type != null) && this.type.equals(rhs.type)))) && ((this.paidAmount == rhs.paidAmount) || ((this.paidAmount != null) && this.paidAmount.equals(rhs.paidAmount)))) && ((this.reconciliation == rhs.reconciliation) || ((this.reconciliation != null) && this.reconciliation.equals(rhs.reconciliation)))) && ((this.finishedAt == rhs.finishedAt) || ((this.finishedAt != null) && this.finishedAt.equals(rhs.finishedAt))));
    }

}
