
package pl.kamil_dywan.allegro;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "offer",
    "quantity",
    "originalPrice",
    "price",
    "reconciliation",
    "selectedAdditionalServices",
    "vouchers",
    "tax",
    "boughtAt"
})
@Generated("jsonschema2pojo")
public class LineItem {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("offer")
    private Offer offer;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("quantity")
    private Integer quantity;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("originalPrice")
    private OriginalPrice originalPrice;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("price")
    private Price price;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("reconciliation")
    private Reconciliation__1 reconciliation;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("selectedAdditionalServices")
    private List<Object> selectedAdditionalServices = new ArrayList<Object>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vouchers")
    private List<Object> vouchers = new ArrayList<Object>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("tax")
    private Tax tax;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("boughtAt")
    private String boughtAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("offer")
    public Offer getOffer() {
        return offer;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("offer")
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("originalPrice")
    public OriginalPrice getOriginalPrice() {
        return originalPrice;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("originalPrice")
    public void setOriginalPrice(OriginalPrice originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("price")
    public Price getPrice() {
        return price;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("price")
    public void setPrice(Price price) {
        this.price = price;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("reconciliation")
    public Reconciliation__1 getReconciliation() {
        return reconciliation;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("reconciliation")
    public void setReconciliation(Reconciliation__1 reconciliation) {
        this.reconciliation = reconciliation;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("selectedAdditionalServices")
    public List<Object> getSelectedAdditionalServices() {
        return selectedAdditionalServices;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("selectedAdditionalServices")
    public void setSelectedAdditionalServices(List<Object> selectedAdditionalServices) {
        this.selectedAdditionalServices = selectedAdditionalServices;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vouchers")
    public List<Object> getVouchers() {
        return vouchers;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vouchers")
    public void setVouchers(List<Object> vouchers) {
        this.vouchers = vouchers;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("tax")
    public Tax getTax() {
        return tax;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("tax")
    public void setTax(Tax tax) {
        this.tax = tax;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("boughtAt")
    public String getBoughtAt() {
        return boughtAt;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("boughtAt")
    public void setBoughtAt(String boughtAt) {
        this.boughtAt = boughtAt;
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
        sb.append(LineItem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("offer");
        sb.append('=');
        sb.append(((this.offer == null)?"<null>":this.offer));
        sb.append(',');
        sb.append("quantity");
        sb.append('=');
        sb.append(((this.quantity == null)?"<null>":this.quantity));
        sb.append(',');
        sb.append("originalPrice");
        sb.append('=');
        sb.append(((this.originalPrice == null)?"<null>":this.originalPrice));
        sb.append(',');
        sb.append("price");
        sb.append('=');
        sb.append(((this.price == null)?"<null>":this.price));
        sb.append(',');
        sb.append("reconciliation");
        sb.append('=');
        sb.append(((this.reconciliation == null)?"<null>":this.reconciliation));
        sb.append(',');
        sb.append("selectedAdditionalServices");
        sb.append('=');
        sb.append(((this.selectedAdditionalServices == null)?"<null>":this.selectedAdditionalServices));
        sb.append(',');
        sb.append("vouchers");
        sb.append('=');
        sb.append(((this.vouchers == null)?"<null>":this.vouchers));
        sb.append(',');
        sb.append("tax");
        sb.append('=');
        sb.append(((this.tax == null)?"<null>":this.tax));
        sb.append(',');
        sb.append("boughtAt");
        sb.append('=');
        sb.append(((this.boughtAt == null)?"<null>":this.boughtAt));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.offer == null)? 0 :this.offer.hashCode()));
        result = ((result* 31)+((this.quantity == null)? 0 :this.quantity.hashCode()));
        result = ((result* 31)+((this.originalPrice == null)? 0 :this.originalPrice.hashCode()));
        result = ((result* 31)+((this.boughtAt == null)? 0 :this.boughtAt.hashCode()));
        result = ((result* 31)+((this.price == null)? 0 :this.price.hashCode()));
        result = ((result* 31)+((this.tax == null)? 0 :this.tax.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.vouchers == null)? 0 :this.vouchers.hashCode()));
        result = ((result* 31)+((this.reconciliation == null)? 0 :this.reconciliation.hashCode()));
        result = ((result* 31)+((this.selectedAdditionalServices == null)? 0 :this.selectedAdditionalServices.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LineItem) == false) {
            return false;
        }
        LineItem rhs = ((LineItem) other);
        return ((((((((((((this.offer == rhs.offer)||((this.offer!= null)&&this.offer.equals(rhs.offer)))&&((this.quantity == rhs.quantity)||((this.quantity!= null)&&this.quantity.equals(rhs.quantity))))&&((this.originalPrice == rhs.originalPrice)||((this.originalPrice!= null)&&this.originalPrice.equals(rhs.originalPrice))))&&((this.boughtAt == rhs.boughtAt)||((this.boughtAt!= null)&&this.boughtAt.equals(rhs.boughtAt))))&&((this.price == rhs.price)||((this.price!= null)&&this.price.equals(rhs.price))))&&((this.tax == rhs.tax)||((this.tax!= null)&&this.tax.equals(rhs.tax))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.vouchers == rhs.vouchers)||((this.vouchers!= null)&&this.vouchers.equals(rhs.vouchers))))&&((this.reconciliation == rhs.reconciliation)||((this.reconciliation!= null)&&this.reconciliation.equals(rhs.reconciliation))))&&((this.selectedAdditionalServices == rhs.selectedAdditionalServices)||((this.selectedAdditionalServices!= null)&&this.selectedAdditionalServices.equals(rhs.selectedAdditionalServices))));
    }

}
