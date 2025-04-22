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
        "messageToSeller",
        "buyer",
        "payment",
        "status",
        "fulfillment",
        "delivery",
        "invoice",
        "lineItems",
        "surcharges",
        "discounts",
        "note",
        "marketplace",
        "summary",
        "updatedAt",
        "revision"
})
@Generated("jsonschema2pojo")
public class Order {

    @JsonProperty("id")
    private String id;

    @JsonProperty("messageToSeller")
    private String messageToSeller;

    @JsonProperty("buyer")
    private Buyer buyer;

    @JsonProperty("payment")
    private Payment payment;

    @JsonProperty("status")
    private String status;

    @JsonProperty("fulfillment")
    private Fulfillment fulfillment;

    @JsonProperty("delivery")
    private Delivery delivery;

    @JsonProperty("invoice")
    private Invoice invoice;

    @JsonProperty("lineItems")
    private List<LineItem> lineItems = new ArrayList<LineItem>();

    @JsonProperty("surcharges")
    private List<Object> surcharges = new ArrayList<Object>();

    @JsonProperty("discounts")
    private List<Object> discounts = new ArrayList<Object>();

    @JsonProperty("note")
    private Note note;

    @JsonProperty("marketplace")
    private Marketplace marketplace;

    @JsonProperty("summary")
    private Summary summary;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("revision")
    private String revision;
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


    @JsonProperty("messageToSeller")
    public String getMessageToSeller() {
        return messageToSeller;
    }


    @JsonProperty("messageToSeller")
    public void setMessageToSeller(String messageToSeller) {
        this.messageToSeller = messageToSeller;
    }


    @JsonProperty("buyer")
    public Buyer getBuyer() {
        return buyer;
    }


    @JsonProperty("buyer")
    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }


    @JsonProperty("payment")
    public Payment getPayment() {
        return payment;
    }


    @JsonProperty("payment")
    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    @JsonProperty("status")
    public String getStatus() {
        return status;
    }


    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }


    @JsonProperty("fulfillment")
    public Fulfillment getFulfillment() {
        return fulfillment;
    }


    @JsonProperty("fulfillment")
    public void setFulfillment(Fulfillment fulfillment) {
        this.fulfillment = fulfillment;
    }


    @JsonProperty("delivery")
    public Delivery getDelivery() {
        return delivery;
    }


    @JsonProperty("delivery")
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }


    @JsonProperty("invoice")
    public Invoice getInvoice() {
        return invoice;
    }


    @JsonProperty("invoice")
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }


    @JsonProperty("lineItems")
    public List<LineItem> getLineItems() {
        return lineItems;
    }


    @JsonProperty("lineItems")
    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }


    @JsonProperty("surcharges")
    public List<Object> getSurcharges() {
        return surcharges;
    }


    @JsonProperty("surcharges")
    public void setSurcharges(List<Object> surcharges) {
        this.surcharges = surcharges;
    }


    @JsonProperty("discounts")
    public List<Object> getDiscounts() {
        return discounts;
    }


    @JsonProperty("discounts")
    public void setDiscounts(List<Object> discounts) {
        this.discounts = discounts;
    }


    @JsonProperty("note")
    public Note getNote() {
        return note;
    }


    @JsonProperty("note")
    public void setNote(Note note) {
        this.note = note;
    }


    @JsonProperty("marketplace")
    public Marketplace getMarketplace() {
        return marketplace;
    }


    @JsonProperty("marketplace")
    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }


    @JsonProperty("summary")
    public Summary getSummary() {
        return summary;
    }


    @JsonProperty("summary")
    public void setSummary(Summary summary) {
        this.summary = summary;
    }


    @JsonProperty("updatedAt")
    public String getUpdatedAt() {
        return updatedAt;
    }


    @JsonProperty("updatedAt")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    @JsonProperty("revision")
    public String getRevision() {
        return revision;
    }


    @JsonProperty("revision")
    public void setRevision(String revision) {
        this.revision = revision;
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
        sb.append(Order.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("messageToSeller");
        sb.append('=');
        sb.append(((this.messageToSeller == null) ? "<null>" : this.messageToSeller));
        sb.append(',');
        sb.append("buyer");
        sb.append('=');
        sb.append(((this.buyer == null) ? "<null>" : this.buyer));
        sb.append(',');
        sb.append("payment");
        sb.append('=');
        sb.append(((this.payment == null) ? "<null>" : this.payment));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null) ? "<null>" : this.status));
        sb.append(',');
        sb.append("fulfillment");
        sb.append('=');
        sb.append(((this.fulfillment == null) ? "<null>" : this.fulfillment));
        sb.append(',');
        sb.append("delivery");
        sb.append('=');
        sb.append(((this.delivery == null) ? "<null>" : this.delivery));
        sb.append(',');
        sb.append("invoice");
        sb.append('=');
        sb.append(((this.invoice == null) ? "<null>" : this.invoice));
        sb.append(',');
        sb.append("lineItems");
        sb.append('=');
        sb.append(((this.lineItems == null) ? "<null>" : this.lineItems));
        sb.append(',');
        sb.append("surcharges");
        sb.append('=');
        sb.append(((this.surcharges == null) ? "<null>" : this.surcharges));
        sb.append(',');
        sb.append("discounts");
        sb.append('=');
        sb.append(((this.discounts == null) ? "<null>" : this.discounts));
        sb.append(',');
        sb.append("note");
        sb.append('=');
        sb.append(((this.note == null) ? "<null>" : this.note));
        sb.append(',');
        sb.append("marketplace");
        sb.append('=');
        sb.append(((this.marketplace == null) ? "<null>" : this.marketplace));
        sb.append(',');
        sb.append("summary");
        sb.append('=');
        sb.append(((this.summary == null) ? "<null>" : this.summary));
        sb.append(',');
        sb.append("updatedAt");
        sb.append('=');
        sb.append(((this.updatedAt == null) ? "<null>" : this.updatedAt));
        sb.append(',');
        sb.append("revision");
        sb.append('=');
        sb.append(((this.revision == null) ? "<null>" : this.revision));
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
        result = ((result * 31) + ((this.summary == null) ? 0 : this.summary.hashCode()));
        result = ((result * 31) + ((this.delivery == null) ? 0 : this.delivery.hashCode()));
        result = ((result * 31) + ((this.note == null) ? 0 : this.note.hashCode()));
        result = ((result * 31) + ((this.marketplace == null) ? 0 : this.marketplace.hashCode()));
        result = ((result * 31) + ((this.messageToSeller == null) ? 0 : this.messageToSeller.hashCode()));
        result = ((result * 31) + ((this.buyer == null) ? 0 : this.buyer.hashCode()));
        result = ((result * 31) + ((this.revision == null) ? 0 : this.revision.hashCode()));
        result = ((result * 31) + ((this.lineItems == null) ? 0 : this.lineItems.hashCode()));
        result = ((result * 31) + ((this.discounts == null) ? 0 : this.discounts.hashCode()));
        result = ((result * 31) + ((this.surcharges == null) ? 0 : this.surcharges.hashCode()));
        result = ((result * 31) + ((this.payment == null) ? 0 : this.payment.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.fulfillment == null) ? 0 : this.fulfillment.hashCode()));
        result = ((result * 31) + ((this.invoice == null) ? 0 : this.invoice.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.status == null) ? 0 : this.status.hashCode()));
        result = ((result * 31) + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Order) == false) {
            return false;
        }
        Order rhs = ((Order) other);
        return ((((((((((((((((((this.summary == rhs.summary) || ((this.summary != null) && this.summary.equals(rhs.summary))) && ((this.delivery == rhs.delivery) || ((this.delivery != null) && this.delivery.equals(rhs.delivery)))) && ((this.note == rhs.note) || ((this.note != null) && this.note.equals(rhs.note)))) && ((this.marketplace == rhs.marketplace) || ((this.marketplace != null) && this.marketplace.equals(rhs.marketplace)))) && ((this.messageToSeller == rhs.messageToSeller) || ((this.messageToSeller != null) && this.messageToSeller.equals(rhs.messageToSeller)))) && ((this.buyer == rhs.buyer) || ((this.buyer != null) && this.buyer.equals(rhs.buyer)))) && ((this.revision == rhs.revision) || ((this.revision != null) && this.revision.equals(rhs.revision)))) && ((this.lineItems == rhs.lineItems) || ((this.lineItems != null) && this.lineItems.equals(rhs.lineItems)))) && ((this.discounts == rhs.discounts) || ((this.discounts != null) && this.discounts.equals(rhs.discounts)))) && ((this.surcharges == rhs.surcharges) || ((this.surcharges != null) && this.surcharges.equals(rhs.surcharges)))) && ((this.payment == rhs.payment) || ((this.payment != null) && this.payment.equals(rhs.payment)))) && ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id)))) && ((this.fulfillment == rhs.fulfillment) || ((this.fulfillment != null) && this.fulfillment.equals(rhs.fulfillment)))) && ((this.invoice == rhs.invoice) || ((this.invoice != null) && this.invoice.equals(rhs.invoice)))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties)))) && ((this.status == rhs.status) || ((this.status != null) && this.status.equals(rhs.status)))) && ((this.updatedAt == rhs.updatedAt) || ((this.updatedAt != null) && this.updatedAt.equals(rhs.updatedAt))));
    }

}
