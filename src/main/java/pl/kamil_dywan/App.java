package pl.kamil_dywan;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.SAXException;
import pl.kamil_dywan.allegro.generated.buyer.Buyer;
import pl.kamil_dywan.allegro.generated.buyer.BuyerAddress;
import pl.kamil_dywan.allegro.generated.invoice.InvoiceAddress;
import pl.kamil_dywan.allegro.generated.invoice.InvoiceCompany;
import pl.kamil_dywan.allegro.generated.invoice.InvoiceNaturalPerson;
import pl.kamil_dywan.allegro.generated.invoice_item.LineItem;
import pl.kamil_dywan.allegro.generated.order.Order;
import pl.kamil_dywan.allegro.generated.order.OrderResponse;
import pl.kamil_dywan.subiektgt.generated.*;
import pl.kamil_dywan.subiektgt.generated.Invoice;
import pl.kamil_dywan.subiektgt.generated.BatchTrailer;
import pl.kamil_dywan.subiektgt.own.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException, SAXException, URISyntaxException, ParseException, DatatypeConfigurationException, JAXBException, XMLStreamException {

        URL allegroOrderFileUrl = App.class.getClassLoader().getResource("./data/order.json");
        URI allegroOrderFileUri = allegroOrderFileUrl.toURI();
        Path allegroOrderFilePath = Path.of(allegroOrderFileUri);
        String allegroOrderStr = Files.readString(allegroOrderFilePath);

        ObjectMapper objectMapper = new ObjectMapper();

        OrderResponse allegroOrderResponse = objectMapper.readValue(allegroOrderStr, OrderResponse.class);
        String allegroOrderOutputStr = objectMapper.writeValueAsString(allegroOrderResponse.getOrders());

        File file = new File("order-output.json");

        if(!file.exists()){
            file.createNewFile();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(allegroOrderOutputStr);
        bufferedWriter.close();

        Order allegroOrder = (Order) allegroOrderResponse.getOrders().get(0);

        InvoiceHead invoiceHead = InvoiceHead.builder()
            .schema(new InvoiceHead.Schema((byte) 3))
            .parameters(new InvoiceHead.Parameters("PL", ",", 20.3f))
            .invoiceType(new InvoiceHead.InvoiceType(InvoiceType.VAT.toString(), Code.INVOICE.toString()))
            .function(new InvoiceHead.Function("", Code.FII.toString()))
            .invoiceCurrency(new InvoiceHead.InvoiceCurrency(new InvoiceHead.InvoiceCurrency.Currency("", Code.PLN.toString())))
            .checksum(81410)
            .build();

        InvoiceReferences invoiceReferences = InvoiceReferences.builder()
            .suppliersInvoiceNumber("2/2025")
            .build();

        pl.kamil_dywan.allegro.generated.invoice.Invoice allegroInvoice = allegroOrder.getInvoice();
        InvoiceAddress supplierCompanyAddress = allegroInvoice.getAddress();
        InvoiceCompany supplierCompany = supplierCompanyAddress.getCompany();
        InvoiceNaturalPerson supplierCompanyPeron = supplierCompanyAddress.getNaturalPerson();

        Supplier subiektSupplier = Supplier.builder()
//            .supplierReferences(new Supplier.SupplierReferences(supplierCompany.getName(), supplierCompany.getTaxId()))
            .party(supplierCompany.getName())
            .address(Supplier.Address.builder()
                .street(supplierCompanyAddress.getStreet())
                .city(supplierCompanyAddress.getCity())
                .postCode(supplierCompanyAddress.getZipCode())
                .build()
            )
            .contact(Supplier.Contact.builder()
                .name(supplierCompanyPeron != null ? supplierCompanyPeron.getFirstName() + " " + supplierCompanyPeron.getLastName() : null)
                .switchboard("")
                .fax("")
                .build()
            )
            .build();

        Buyer allegroBuyer = allegroOrder.getBuyer();
        BuyerAddress allegroBuyerAddress = allegroBuyer.getAddress();

        pl.kamil_dywan.subiektgt.generated.Buyer subiektBuyer = pl.kamil_dywan.subiektgt.generated.Buyer.builder()
//            .buyerReferences(
//                new pl.kamil_dywan.subiektgt.generated.Buyer.BuyerReferences(
//                    allegroBuyer.getCompanyName() != null ? allegroBuyer.getCompanyName() : allegroBuyer.getFirstName() + " " + allegroBuyer.getLastName(),
//                    allegroBuyer.
//                )
//            )
            .party(allegroBuyer.getCompanyName())
            .address(pl.kamil_dywan.subiektgt.generated.Buyer.Address.builder()
                .street(allegroBuyerAddress.getStreet())
                .city(allegroBuyerAddress.getCity())
                .postCode(allegroBuyerAddress.getPostCode())
                .build()
            )
            .contact(pl.kamil_dywan.subiektgt.generated.Buyer.Contact.builder()
                .name(allegroBuyer.getFirstName() + " " + allegroBuyer.getLastName())
                .switchboard(allegroBuyer.getPhoneNumber())
                .build()
            )
            .build();

        List<LineItem> allegroLineItems =  allegroOrder.getLineItems();

        AtomicInteger invoiceNumber = new AtomicInteger(0);

        HashMap<TaxRate, TaxSubTotal> taxSubTotals = new LinkedHashMap<>();

        taxSubTotals.put(TaxRate.H,
            TaxSubTotal.builder()
                .code(Code.PLN.toString())
                .taxRate(
                    new TaxSubTotal.TaxRate(
                        (byte) TaxRate.H.getValue().intValue(),
                        TaxRate.H.getCode().toString()
                    )
                )
                .taxableValueAtRate(BigDecimal.ZERO.toString())
                .taxAtRate(BigDecimal.ZERO.toString())
                .netPaymentAtRate(BigDecimal.ZERO.toString())
                .grossPaymentAtRate(BigDecimal.ZERO.toString())
                .taxCurrency("")
                .build()
        );

        taxSubTotals.put(TaxRate.L,
            TaxSubTotal.builder()
                .code(Code.PLN.toString())
                .taxRate(
                    new TaxSubTotal.TaxRate(
                        (byte) TaxRate.L.getValue().intValue(),
                        TaxRate.L.getCode().toString()
                    )
                )
                .taxableValueAtRate(BigDecimal.ZERO.toString())
                .taxAtRate(BigDecimal.ZERO.toString())
                .netPaymentAtRate(BigDecimal.ZERO.toString())
                .grossPaymentAtRate(BigDecimal.ZERO.toString())
                .taxCurrency("")
                .build()
        );

        taxSubTotals.put(TaxRate.Z,
            TaxSubTotal.builder()
                .code(Code.PLN.toString())
                .taxRate(
                    new TaxSubTotal.TaxRate(
                        (byte) TaxRate.Z.getValue().intValue(),
                        TaxRate.Z.getCode().toString()
                    )
                )
                .taxableValueAtRate(BigDecimal.ZERO.toString())
                .taxAtRate(BigDecimal.ZERO.toString())
                .netPaymentAtRate(BigDecimal.ZERO.toString())
                .grossPaymentAtRate(BigDecimal.ZERO.toString())
                .taxCurrency("")
                .build()
        );

        List<InvoiceLine> invoiceLines = allegroLineItems.stream()
            .map(allegroLineItem -> {

                BigDecimal quantity = new BigDecimal(allegroLineItem.getQuantity());
                BigDecimal totalPrice = new BigDecimal(allegroLineItem.getPrice().getAmount()).multiply(quantity);

                BigDecimal percentageTaxRateValue = BigDecimal.ZERO;

                if(allegroLineItem.getTax() != null){

                    percentageTaxRateValue = new BigDecimal(allegroLineItem.getTax().getRate());
                }

                TaxRate taxRate = TaxRate.getByValue(percentageTaxRateValue.intValue());

                BigDecimal taxRateValue = percentageTaxRateValue.divide(BigDecimal.valueOf(100));

                BigDecimal totalTaxValue = totalPrice.multiply(taxRateValue);

                BigDecimal unitTaxValue = totalTaxValue.divide(new BigDecimal(allegroLineItem.getQuantity()));

                BigDecimal rawUnitPrice = totalPrice
                    .subtract(totalTaxValue)
                    .divide(quantity);

                BigDecimal rawTotalPrice = rawUnitPrice
                    .multiply(quantity);

                totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
                unitTaxValue = unitTaxValue.setScale(2, RoundingMode.HALF_UP);
                taxRateValue = taxRateValue.setScale(2, RoundingMode.HALF_UP);
                totalTaxValue = totalTaxValue.setScale(2, RoundingMode.HALF_UP);
                rawUnitPrice = rawUnitPrice.setScale(2, RoundingMode.HALF_UP);
                rawTotalPrice = rawTotalPrice.setScale(2, RoundingMode.HALF_UP);

                TaxSubTotal taxSubTotal = taxSubTotals.get(taxRate);

                taxSubTotal.setTaxableValueAtRate(
                    new BigDecimal(taxSubTotal.getTaxableValueAtRate()).add(rawTotalPrice).toString()
                );

                taxSubTotal.setTaxAtRate(
                    new BigDecimal(taxSubTotal.getTaxAtRate()).add(totalTaxValue).toString()
                );

                taxSubTotal.setNetPaymentAtRate(
                    new BigDecimal(taxSubTotal.getNetPaymentAtRate()).add(totalPrice).toString()
                );

                taxSubTotal.setGrossPaymentAtRate(taxSubTotal.getNetPaymentAtRate());

                return InvoiceLine.builder()
                    .lineNumber((byte) invoiceNumber.incrementAndGet())
                    .product(InvoiceLine.Product.builder()
                        .suppliersProductCode(allegroLineItem.getOffer().getExternal() != null ? allegroLineItem.getOffer().getExternal().getId() : null)
                        .description(allegroLineItem.getOffer().getName())
                        .build()
                    )
                        .quantity(new InvoiceLine.Quantity(
                            (byte) allegroLineItem.getOffer().getProductSet().getProducts().size(),
                            (byte) quantity.intValue(),
                            UOMCode.UNIT.toString()
                        ))
                        .price(new InvoiceLine.Price(rawUnitPrice.toString()))
                        .percentDiscount(
                            new InvoiceLine.PercentDiscount(
                                new InvoiceLine.PercentDiscount.Type("", Code.LID.toString()), (byte) 0
                            )
                        )
                        .lineTax(
                            new InvoiceLine.LineTax(
                                new InvoiceLine.LineTax.TaxRate(
                                    (byte) taxRate.getValue().intValue(),
                                    TaxRate.getByValue(taxRateValue.intValue()).getCode().toString()
                                ),
                                totalTaxValue.toString()
                            )
                        )
                        .lineTotal(totalPrice.toString())
                        .invoiceLineInformation(allegroLineItem.getOffer().getName())
                        .build();
            })
            .collect(Collectors.toList());

        BigDecimal totalInvoiceLines = invoiceLines.stream()
            .map(invoiceLine -> new BigDecimal(invoiceLine.getLineTotal()))
            .reduce(((invoiceLine1, invoiceLine2) -> invoiceLine1.add(invoiceLine2)))
            .get();

        InvoiceTotal invoiceTotal = InvoiceTotal.builder()
            .numberOfLines((byte) invoiceLines.size())
            .numberOfTaxRates((byte) taxSubTotals.keySet().stream()
                .map(taxSubTotal -> taxSubTotal.getValue())
                .distinct().count()
            )
            .lineValueTotal(totalInvoiceLines.toString())
            .taxableTotal(totalInvoiceLines.toString())
            .taxTotal(taxSubTotals.values().stream()
                .map(taxSubTotal -> new BigDecimal(taxSubTotal.getTaxAtRate()))
                .reduce((taxAtRate, taxAtRate1) -> taxAtRate.add(taxAtRate1))
                .get().toString()
            )
            .netPaymentTotal(taxSubTotals.values().stream()
                .map(taxSubTotal -> new BigDecimal(taxSubTotal.getNetPaymentAtRate()))
                .reduce((totalPrice, totalPrice1) -> totalPrice.add(totalPrice1))
                .get().toString()
            )
            .grossPaymentTotal(taxSubTotals.values().stream()
                .map(taxSubTotal -> new BigDecimal(taxSubTotal.getNetPaymentAtRate()))
                .reduce((totalPrice, totalPrice1) -> totalPrice.add(totalPrice1))
                .get().toString()
            )
            .build();

        Invoice subiektInvoice = Invoice.builder()
            .invoiceHead(invoiceHead)
//            .invoiceReferences(invoiceReferences)
            .invoiceDate(convert(allegroOrder.getPayment().getFinishedAt()))
            .cityOfIssue(allegroInvoice.getAddress().getCity())
            .taxPointDate(convert(allegroInvoice.getDueDate()))
            .supplier(subiektSupplier)
            .buyer(subiektBuyer)
            .invoiceLines(invoiceLines)
            .narrative("FS - płatność gotówka karta kredyt przelew i kredyt kupiecki")
            .specialInstructions("dokument liczony wg cen netto")
            .settlement(
                new Settlement(
                    new Settlement.SettlementTerms(
                        convert(allegroInvoice.getDueDate()),
                        Code.Code14I.toString()
                    )
                )
            )
            .taxSubTotals(taxSubTotals.values().stream().toList())
            .invoiceTotal(invoiceTotal)
            .build();

        BatchTrailer batchTrailer = BatchTrailer.builder()
            .itemCurrency(new ItemCurrency.ItemCurrency(new ItemCurrency.ItemCurrency.Currency("", Code.PLN.toString())))
            .checksum("")
            .build();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Batch batch = Batch.builder()
            .date(LocalDate.now().format(formatter))
            .number("1")
            .supplierName(supplierCompany.getName())
            .docType(DocType.INVOICE.toString())
            .invoices(List.of(subiektInvoice))
            .batchTrailer(batchTrailer)
            .build();

        File subiektOutputFile = new File("./subiekt.xml");

        if(!subiektOutputFile.exists()){

            subiektOutputFile.createNewFile();
        }

        JAXBContext context = JAXBContext.newInstance(Batch.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_ENCODING, "windows-1250");
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(batch, subiektOutputFile);

        class XMLReaderWithoutNamespace extends StreamReaderDelegate {
            public XMLReaderWithoutNamespace(XMLStreamReader reader) {
                super(reader);
            }
            @Override
            public String getAttributeNamespace(int arg0) {
                return "";
            }
            @Override
            public String getNamespaceURI() {
                return "";
            }
        }

        JAXBContext jc = JAXBContext.newInstance(Batch.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();

        URL subiektTestURL = App.class.getClassLoader().getResource("./data/aa.xml");
        URI subiektTestURI = subiektTestURL.toURI();
        Path subiektTestPath = Path.of(subiektTestURI);
        File subiektTestFile = subiektTestPath.toFile();

        InputStream is = new FileInputStream(subiektTestFile);
        XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(is);
        XMLReaderWithoutNamespace xr = new XMLReaderWithoutNamespace(xsr);

        // unmarshall, note that it's better to reuse JAXBContext, as newInstance()
        // calls are pretty expensive

        Batch batch1 = (Batch) unmarshaller.unmarshal(xr);

        is.close();

        File subiektOutputFile1 = new File("./subiekt-output.xml");

        if(!subiektOutputFile1.exists()){

            subiektOutputFile1.createNewFile();
        }

        JAXBContext context1 = JAXBContext.newInstance(Batch.class);
        Marshaller marshaller1 = context1.createMarshaller();

        marshaller1.setProperty(Marshaller.JAXB_ENCODING, "windows-1250");
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller1.marshal(batch1, subiektOutputFile1);
    }

    public static XMLGregorianCalendar convert(String value) throws ParseException, DatatypeConfigurationException {

        value = value.split("T")[0];

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        Date gotDate = fmt.parse(value);
        String gotDateStr = fmt.format(gotDate);

       return DatatypeFactory.newInstance().newXMLGregorianCalendar(gotDateStr);
    }
}
