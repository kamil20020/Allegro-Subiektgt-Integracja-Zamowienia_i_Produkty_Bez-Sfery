package pl.kamil_dywan.mapper.integration;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.allegro.generated.Payment;
import pl.kamil_dywan.external.allegro.generated.buyer.BuyerAddress;
import pl.kamil_dywan.external.allegro.generated.delivery.Delivery;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceCompany;
import pl.kamil_dywan.external.allegro.generated.invoice.InvoiceNaturalPerson;
import pl.kamil_dywan.external.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.external.allegro.generated.order.Order;
import pl.kamil_dywan.external.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.external.subiektgt.generated.Address;
import pl.kamil_dywan.external.subiektgt.generated.Batch;
import pl.kamil_dywan.external.subiektgt.generated.Contact;
import pl.kamil_dywan.external.subiektgt.generated.Invoice;
import pl.kamil_dywan.external.subiektgt.generated.buyer.Buyer;
import pl.kamil_dywan.external.subiektgt.generated.invoice_line.InvoiceLine;
import pl.kamil_dywan.external.subiektgt.generated.supplier.Supplier;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;
import pl.kamil_dywan.file.read.XMLFileReader;
import pl.kamil_dywan.mapper.BatchMapper;
import pl.kamil_dywan.mapper.InvoiceMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceMapperTestIT {

    FileReader<OrderResponse> allegroOrderReader = new JSONFileReader<>(OrderResponse.class);

    @Test
    void shouldMap() throws URISyntaxException, IOException {

        //given
        OrderResponse allegroOrderResponse = allegroOrderReader.load("data/allegro/real-order-1.json");
        Order allegroOrder = allegroOrderResponse.getOrders().get(0);
        Payment allegroPayment = allegroOrder.getPayment();
        pl.kamil_dywan.external.allegro.generated.invoice.Invoice allegroInvoice = allegroOrder.getInvoice();
        InvoiceAddress allegroInvoiceAddress = allegroInvoice.getAddress();
        InvoiceCompany allegroInvoiceCompany = allegroInvoiceAddress.getCompany();
        InvoiceNaturalPerson allegroInvoiceNaturalPerson = allegroInvoiceAddress.getNaturalPerson();;
        pl.kamil_dywan.external.allegro.generated.buyer.Buyer allegroBuyer = allegroOrder.getBuyer();
        BuyerAddress allegroBuyerAddress = allegroBuyer.getAddress();
        Delivery allegroDelivery = allegroOrder.getDelivery();
        LineItem allegroLineItem = allegroOrder.getLineItems().get(0);

        //when
        Invoice gotInvoice = InvoiceMapper.map(allegroOrder);
        Buyer gotBuyer = gotInvoice.getBuyer();
        Contact gotBuyerContact = gotBuyer.getContact();
        Address gotBuyerAddress = gotBuyer.getAddress();
        Supplier gotSupplier = gotInvoice.getSupplier();
        Contact gotSupplierContact = gotSupplier.getContact();
        Address gotSupplierAddress = gotSupplier.getAddress();
        InvoiceLine gotInvoiceLine = gotInvoice.getInvoiceLines().get(0);

        //then
        assertNotNull(gotInvoice);
        assertNotNull(gotInvoice.getInvoiceLines());
        assertNotNull(gotInvoice.getTaxSubTotals());
        assertNotNull(gotInvoice.getInvoiceTotal());
        assertNotNull(gotInvoice.getInvoiceHead());
        assertEquals(allegroPayment.getFinishedAt().toLocalDate(), gotInvoice.getInvoiceDate());
        assertEquals(allegroInvoiceAddress.getCity(), gotInvoice.getCityOfIssue());
        assertEquals(allegroInvoice.getDueDate(), gotInvoice.getTaxPointDate());

        assertEquals(allegroBuyer.getFirstName() + " " + allegroBuyer.getLastName(), gotBuyerContact.getName());
        assertEquals(allegroBuyer.getCompanyName(), gotBuyer.getParty());
        assertEquals(allegroBuyer.getPhoneNumber(), gotBuyerContact.getSwitchboard());
        assertEquals(allegroBuyerAddress.getCity(), gotBuyerAddress.getCity());
        assertEquals(allegroBuyerAddress.getStreet(), gotBuyerAddress.getStreet());
        assertEquals(allegroBuyerAddress.getPostCode(), gotBuyerAddress.getPostCode());

        assertEquals(allegroInvoiceCompany.getName(), gotSupplier.getParty());
        assertEquals(allegroInvoiceAddress.getCity(), gotSupplierAddress.getCity());
        assertEquals(allegroInvoiceAddress.getStreet(), gotSupplierAddress.getStreet());
        assertEquals(allegroInvoiceAddress.getZipCode(), gotSupplierAddress.getPostCode());

        assertEquals(allegroLineItem.getQuantity(), gotInvoiceLine.getQuantity().getAmount());
        assertEquals(allegroLineItem.getPrice().getAmount(), gotInvoiceLine.getLineTotal());
        assertEquals(allegroLineItem.getQuantity(), gotInvoiceLine.getQuantity().getAmount());
        assertEquals(allegroLineItem.getQuantity(), gotInvoiceLine.getQuantity().getAmount());

//        assertEquals(allegroDelivery.getCost().getAmount(), a);
    }
}