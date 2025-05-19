package pl.kamil_dywan.model.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.kamil_dywan.external.allegro.generated.invoice.Invoice;
import pl.kamil_dywan.external.allegro.generated.order.Order;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @ParameterizedTest
    @CsvSource(value = {
        "true",
        "false"
    })
    void shouldGetHasInvoice(boolean doesOrderHaveInvoice) {

        //given
        Invoice invoice = Invoice.builder()
            .required(doesOrderHaveInvoice)
            .build();

        Order order = Order.builder()
            .invoice(invoice)
            .build();

        //when
        boolean gotResult = order.hasInvoice();

        //then
        assertEquals(doesOrderHaveInvoice, gotResult);
    }

    @Test
    void shouldGetIsBuyerCompany() {


    }

    @Test
    void shouldGetClientName() {


    }

}