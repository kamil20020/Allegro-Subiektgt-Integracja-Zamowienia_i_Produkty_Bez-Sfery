package pl.kamil_dywan.allegro;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "from",
        "to",
        "guaranteed",
        "dispatch"
})
@Generated("jsonschema2pojo")
public class Time {

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("guaranteed")
    private Guaranteed guaranteed;

    @JsonProperty("dispatch")
    private Dispatch dispatch;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("from")
    public String getFrom() {
        return from;
    }


    @JsonProperty("from")
    public void setFrom(String from) {
        this.from = from;
    }


    @JsonProperty("to")
    public String getTo() {
        return to;
    }


    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }


    @JsonProperty("guaranteed")
    public Guaranteed getGuaranteed() {
        return guaranteed;
    }


    @JsonProperty("guaranteed")
    public void setGuaranteed(Guaranteed guaranteed) {
        this.guaranteed = guaranteed;
    }


    @JsonProperty("dispatch")
    public Dispatch getDispatch() {
        return dispatch;
    }


    @JsonProperty("dispatch")
    public void setDispatch(Dispatch dispatch) {
        this.dispatch = dispatch;
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
        sb.append(Time.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("from");
        sb.append('=');
        sb.append(((this.from == null) ? "<null>" : this.from));
        sb.append(',');
        sb.append("to");
        sb.append('=');
        sb.append(((this.to == null) ? "<null>" : this.to));
        sb.append(',');
        sb.append("guaranteed");
        sb.append('=');
        sb.append(((this.guaranteed == null) ? "<null>" : this.guaranteed));
        sb.append(',');
        sb.append("dispatch");
        sb.append('=');
        sb.append(((this.dispatch == null) ? "<null>" : this.dispatch));
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
        result = ((result * 31) + ((this.from == null) ? 0 : this.from.hashCode()));
        result = ((result * 31) + ((this.guaranteed == null) ? 0 : this.guaranteed.hashCode()));
        result = ((result * 31) + ((this.to == null) ? 0 : this.to.hashCode()));
        result = ((result * 31) + ((this.dispatch == null) ? 0 : this.dispatch.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Time) == false) {
            return false;
        }
        Time rhs = ((Time) other);
        return ((((((this.from == rhs.from) || ((this.from != null) && this.from.equals(rhs.from))) && ((this.guaranteed == rhs.guaranteed) || ((this.guaranteed != null) && this.guaranteed.equals(rhs.guaranteed)))) && ((this.to == rhs.to) || ((this.to != null) && this.to.equals(rhs.to)))) && ((this.dispatch == rhs.dispatch) || ((this.dispatch != null) && this.dispatch.equals(rhs.dispatch)))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
