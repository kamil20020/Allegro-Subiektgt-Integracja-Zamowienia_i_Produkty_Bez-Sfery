package pl.kamil_dywan.file.write;

import org.junit.jupiter.api.Test;
import pl.kamil_dywan.external.subiektgt.generated.InvoiceBatch;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.XMLFileReader;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class XMLFileWriterTest {

    private static final String validBatchStr = """
                <?xml version="1.0" encoding="windows-1250" standalone="yes"?>
                <Batch Date="2025-04-20" Number="1" SupplierName="Firma przykładowa systemu InsERT GT" DocType="Invoice">
                    <Invoice>
                        <InvoiceHead>
                            <Schema>
                                <Version>3</Version>
                            </Schema>
                            <Parameters>
                                <Language>PL</Language>
                                <DecimalSeparator>,</DecimalSeparator>
                                <Precision>20.3</Precision>
                            </Parameters>
                            <InvoiceType Code="INV">Faktura VAT</InvoiceType>
                            <Function Code="FII"></Function>
                            <InvoiceCurrency>
                                <Currency Code="PLN"/>
                            </InvoiceCurrency>
                            <Checksum>81410</Checksum>
                        </InvoiceHead>
                        <InvoiceReferences>
                            <SuppliersInvoiceNumber>5/2025</SuppliersInvoiceNumber>
                        </InvoiceReferences>
                        <InvoiceDate>2025-02-16</InvoiceDate>
                        <CityOfIssue>Wrocław</CityOfIssue>
                        <TaxPointDate>2025-02-16</TaxPointDate>
                        <Supplier>
                            <SupplierReferences>
                                <BuyersCodeForSupplier>GT</BuyersCodeForSupplier>
                                <TaxNumber>111-111-11-11</TaxNumber>
                            </SupplierReferences>
                            <Party>test-przykladowe-dane</Party>
                            <Address>
                                <Street>Bławatkowa 25/3</Street>
                                <City>Wrocław</City>
                                <PostCode>54-445</PostCode>
                            </Address>
                            <Contact>
                                <Name>Szef</Name>
                                <Switchboard>354-65-89</Switchboard>
                                <Fax></Fax>
                            </Contact>
                        </Supplier>
                        <Buyer>
                            <BuyerReferences>
                                <SuppliersCodeForBuyer>ODEON</SuppliersCodeForBuyer>
                                <TaxNumber>5460042333</TaxNumber>
                            </BuyerReferences>
                            <Party>Drogeria ODEON</Party>
                            <Address>
                                <Street>Bałtycka  13/6 </Street>
                                <City>Koszalin</City>
                                <PostCode>74-439</PostCode>
                            </Address>
                            <Contact>
                                <Name>Krystyna Sikorska</Name>
                                <Switchboard>94 433-35-26</Switchboard>
                                <Fax></Fax>
                            </Contact>
                        </Buyer>
                        <InvoiceLine>
                            <LineNumber>1</LineNumber>
                            <Product>
                                <SuppliersProductCode>POYAR02</SuppliersProductCode>
                                <Description>Pomadka długotrwała 02</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>60</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>153</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="Z">0</TaxRate>
                                <TaxValue>0</TaxValue>
                            </LineTax>
                            <LineTotal>8904,6</LineTotal>
                            <InvoiceLineInformation>Szminka z błyszczykiem</InvoiceLineInformation>
                        </InvoiceLine>
                        <InvoiceLine>
                            <LineNumber>2</LineNumber>
                            <Product>
                                <SuppliersProductCode>WOBLACK100</SuppliersProductCode>
                                <Description>Black Tiger woda toaletowa 100ml</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>50</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>403,18</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="H">23</TaxRate>
                                <TaxValue>4636,57</TaxValue>
                            </LineTax>
                            <LineTotal>24795,57</LineTotal>
                            <InvoiceLineInformation>Produkt dla prawdziwych mężczyzn</InvoiceLineInformation>
                        </InvoiceLine>
                        <InvoiceLine>
                            <LineNumber>3</LineNumber>
                            <Product>
                                <SuppliersProductCode>DZSO100</SuppliersProductCode>
                                <Description>So dezodorant perfumowany 100ml</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>50</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>43,18</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="H">23</TaxRate>
                                <TaxValue>496,57</TaxValue>
                            </LineTax>
                            <LineTotal>2655,57</LineTotal>
                            <InvoiceLineInformation>Dezodorant o mocnym i długotrwałym zapachu</InvoiceLineInformation>
                        </InvoiceLine>
                        <InvoiceLine>
                            <LineNumber>4</LineNumber>
                            <Product>
                                <SuppliersProductCode>DZSO50</SuppliersProductCode>
                                <Description>So dezodorant perfumowany 50ml</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>50</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>260,96</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="H">23</TaxRate>
                                <TaxValue>3001,04</TaxValue>
                            </LineTax>
                            <LineTotal>16049,04</LineTotal>
                            <InvoiceLineInformation>Dezodorant o mocnym i długotrwałym zapachu. Największe opakowanie</InvoiceLineInformation>
                        </InvoiceLine>
                        <InvoiceLine>
                            <LineNumber>5</LineNumber>
                            <Product>
                                <SuppliersProductCode>POYAR03</SuppliersProductCode>
                                <Description>Pomadka długotrwała 03</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>50</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>171</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="Z">0</TaxRate>
                                <TaxValue>0</TaxValue>
                            </LineTax>
                            <LineTotal>8550</LineTotal>
                            <InvoiceLineInformation>Szminka z błyszczykiem</InvoiceLineInformation>
                        </InvoiceLine>
                        <InvoiceLine>
                            <LineNumber>6</LineNumber>
                            <Product>
                                <SuppliersProductCode>DZSO20</SuppliersProductCode>
                                <Description>So dezodorant perfumowany 20ml</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>30</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>122,78</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="H">23</TaxRate>
                                <TaxValue>847,18</TaxValue>
                            </LineTax>
                            <LineTotal>4530,58</LineTotal>
                            <InvoiceLineInformation>Dezodorant o mocnym i długotrwałym zapachu</InvoiceLineInformation>
                        </InvoiceLine>
                        <InvoiceLine>
                            <LineNumber>7</LineNumber>
                            <Product>
                                <SuppliersProductCode>PESO50</SuppliersProductCode>
                                <Description>So perfumy 50ml</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>10</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>396</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="L">8</TaxRate>
                                <TaxValue>316,8</TaxValue>
                            </LineTax>
                            <LineTotal>4276,8</LineTotal>
                            <InvoiceLineInformation>Perfumy o mocnym i długotrwałym zapachu</InvoiceLineInformation>
                        </InvoiceLine>
                        <InvoiceLine>
                            <LineNumber>8</LineNumber>
                            <Product>
                                <SuppliersProductCode>DZFOREVER</SuppliersProductCode>
                                <Description>Forever dezodorant 100ml</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>10</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>278,1</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="H">23</TaxRate>
                                <TaxValue>639,63</TaxValue>
                            </LineTax>
                            <LineTotal>3420,63</LineTotal>
                            <InvoiceLineInformation>Dezodorant o miłym leśnym zapachu</InvoiceLineInformation>
                        </InvoiceLine>
                        <InvoiceLine>
                            <LineNumber>9</LineNumber>
                            <Product>
                                <SuppliersProductCode>DOSTAWA</SuppliersProductCode>
                                <Description>Dostawa do klienta</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>1</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>40</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="H">23</TaxRate>
                                <TaxValue>9,2</TaxValue>
                            </LineTax>
                            <LineTotal>49,2</LineTotal>
                            <InvoiceLineInformation></InvoiceLineInformation>
                        </InvoiceLine>
                        <InvoiceLine>
                            <LineNumber>10</LineNumber>
                            <Product>
                                <SuppliersProductCode>OPKKR</SuppliersProductCode>
                                <Description>Paleta duża</Description>
                            </Product>
                            <Quantity UOMCode="szt.">
                                <Packsize>1</Packsize>
                                <Amount>1</Amount>
                            </Quantity>
                            <Price>
                                <UnitPrice>26,25</UnitPrice>
                            </Price>
                            <PercentDiscount>
                                <Type Code="LID"></Type>
                                <Percentage>0</Percentage>
                            </PercentDiscount>
                            <LineTax>
                                <TaxRate Code="Z">0</TaxRate>
                                <TaxValue>0</TaxValue>
                            </LineTax>
                            <LineTotal>26,25</LineTotal>
                            <InvoiceLineInformation>Paleta duża</InvoiceLineInformation>
                        </InvoiceLine>
                        <Narrative>FS - płatność gotówka karta kredyt przelew i kredyt kupiecki</Narrative>
                        <SpecialInstructions>dokument liczony wg cen netto</SpecialInstructions>
                        <Settlement>
                            <SettlementTerms Code="00I">2025-02-16</SettlementTerms>
                        </Settlement>
                        <TaxSubTotal Code="PLN">
                            <TaxRate Code="H">23</TaxRate>
                            <TaxableValueAtRate>41870,4</TaxableValueAtRate>
                            <TaxAtRate>9630,19</TaxAtRate>
                            <NetPaymentAtRate>51500,59</NetPaymentAtRate>
                            <GrossPaymentAtRate>51500,59</GrossPaymentAtRate>
                            <TaxCurrency></TaxCurrency>
                        </TaxSubTotal>
                        <TaxSubTotal Code="PLN">
                            <TaxRate Code="L">8</TaxRate>
                            <TaxableValueAtRate>3960</TaxableValueAtRate>
                            <TaxAtRate>316,8</TaxAtRate>
                            <NetPaymentAtRate>4276,8</NetPaymentAtRate>
                            <GrossPaymentAtRate>4276,8</GrossPaymentAtRate>
                            <TaxCurrency></TaxCurrency>
                        </TaxSubTotal>
                        <TaxSubTotal Code="PLN">
                            <TaxRate Code="Z">0</TaxRate>
                            <TaxableValueAtRate>17454,6</TaxableValueAtRate>
                            <TaxAtRate>0</TaxAtRate>
                            <NetPaymentAtRate>17454,6</NetPaymentAtRate>
                            <GrossPaymentAtRate>17454,6</GrossPaymentAtRate>
                            <TaxCurrency></TaxCurrency>
                        </TaxSubTotal>
                        <InvoiceTotal>
                            <NumberOfLines>10</NumberOfLines>
                            <NumberOfTaxRates>3</NumberOfTaxRates>
                            <LineValueTotal>63285</LineValueTotal>
                            <TaxableTotal>63285</TaxableTotal>
                            <TaxTotal>9946,99</TaxTotal>
                            <NetPaymentTotal>73231,99</NetPaymentTotal>
                            <GrossPaymentTotal>73231,99</GrossPaymentTotal>
                        </InvoiceTotal>
                    </Invoice>
                    <BatchTrailer>
                        <ItemCurrency>
                            <Currency Code="PLN"/>
                        </ItemCurrency>
                        <Checksum></Checksum>
                    </BatchTrailer>
                </Batch>
            """;

    private static final FileWriter<InvoiceBatch> fileWriter = new XMLFileWriter<>(InvoiceBatch.class);
    private static final FileReader<InvoiceBatch> fileReader = new XMLFileReader<>(InvoiceBatch.class);

    @Test
    void shouldSave() throws Exception {

        //given
        String toSaveNormalFilePath = "./target/classes/subiekt-test.xml";
        String savedMavenFilePath = "subiekt-test.xml";

        //when
        InvoiceBatch toSaveBatch = fileReader.loadFromStr(validBatchStr);
        fileWriter.save(toSaveNormalFilePath, toSaveBatch);

        File savedFile = FileReader.loadFile(savedMavenFilePath);
        Path savedFilePath = savedFile.toPath();
        String gotSavedBatchStr = Files.readString(savedFilePath, Charset.forName("windows-1250")).replaceAll("\\s", "");

        //then
        assertEquals(validBatchStr.replaceAll("\\s", ""), gotSavedBatchStr);
    }

    @Test
    public void shouldSaveToString() throws Exception {

        //given

        //when
        InvoiceBatch toSaveBatch = fileReader.loadFromStr(validBatchStr);
        String gotBatchStr = fileWriter.writeToStr(toSaveBatch).replaceAll("\\s", "");

        //then
        assertEquals(validBatchStr.replaceAll("\\s", ""), gotBatchStr);
    }
}