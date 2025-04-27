package pl.kamil_dywan.external.subiektgt.generated;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;
import pl.kamil_dywan.external.subiektgt.own.BigDecimalAdapter;
import pl.kamil_dywan.external.subiektgt.own.Code;
import pl.kamil_dywan.external.subiektgt.own.TaxRateCodeMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {
        "taxRate",
        "taxableValueAtRate",
        "taxAtRate",
        "netPaymentAtRate",
        "grossPaymentAtRate",
        "taxCurrency"
    }
)
@XmlRootElement(name = "TaxSubTotal")
public class TaxSubTotal {

    @XmlElement(name = "TaxRate", required = true)
    protected TaxRate taxRate;

    @XmlElement(name = "TaxableValueAtRate", required = true)
    @XmlJavaTypeAdapter(value = BigDecimalAdapter.class)
    protected BigDecimal taxableValueAtRate = BigDecimal.ZERO;

    @XmlElement(name = "TaxAtRate", required = true)
    @XmlJavaTypeAdapter(value = BigDecimalAdapter.class)
    protected BigDecimal taxAtRate = BigDecimal.ZERO;

    @XmlElement(name = "NetPaymentAtRate", required = true)
    @XmlJavaTypeAdapter(value = BigDecimalAdapter.class)
    protected BigDecimal netPaymentAtRate = BigDecimal.ZERO;

    @XmlElement(name = "GrossPaymentAtRate", required = true)
    @XmlJavaTypeAdapter(value = BigDecimalAdapter.class)
    protected BigDecimal grossPaymentAtRate = BigDecimal.ZERO;

    @XmlElement(name = "TaxCurrency", required = true)
    protected String taxCurrency = null;

    @XmlAttribute(name = "Code")
    protected Code code;

    public static TaxSubTotal getEmpty(TaxRateCodeMapping taxRateCodeMapping){

        TaxRate taxRate = new TaxRate(
            new BigDecimal(taxRateCodeMapping.getValue()),
            taxRateCodeMapping.getCode()
        );

        return TaxSubTotal.builder()
            .code(Code.PLN)
            .taxRate(taxRate)
            .taxableValueAtRate(BigDecimal.ZERO)
            .taxAtRate(BigDecimal.ZERO)
            .netPaymentAtRate(BigDecimal.ZERO)
            .grossPaymentAtRate(BigDecimal.ZERO)
            .build();
    }

    public void update(BigDecimal taxableValueAtRate, BigDecimal taxAtRate, BigDecimal netPaymentAtRate){

        this.taxableValueAtRate = this.taxableValueAtRate.add(taxableValueAtRate);
        this.taxAtRate = this.taxAtRate.add(taxAtRate);
        this.netPaymentAtRate = this.netPaymentAtRate.add(netPaymentAtRate);
        this.grossPaymentAtRate = BigDecimal.valueOf(this.netPaymentAtRate.doubleValue());
    }

    public void scale(int scale, RoundingMode roundingMode){

        taxableValueAtRate = taxableValueAtRate.setScale(scale, roundingMode);
        taxAtRate = taxAtRate.setScale(scale, roundingMode);
        netPaymentAtRate = netPaymentAtRate.setScale(scale, roundingMode);
        grossPaymentAtRate = grossPaymentAtRate.setScale(scale, roundingMode);
    }

    public static Map<TaxRateCodeMapping, TaxSubTotal> getEmptyMappingsForAllTaxesRates(){

        Map<TaxRateCodeMapping, TaxSubTotal> taxSubTotalsMappings = new LinkedHashMap<>();

        taxSubTotalsMappings.put(TaxRateCodeMapping.H, getEmpty(TaxRateCodeMapping.H));
        taxSubTotalsMappings.put(TaxRateCodeMapping.L, getEmpty(TaxRateCodeMapping.L));
        taxSubTotalsMappings.put(TaxRateCodeMapping.Z, getEmpty(TaxRateCodeMapping.Z));

        return taxSubTotalsMappings;
    }

    public static Map<TaxRateCodeMapping, Integer> getTaxesRatesOccurs(){

        Map<TaxRateCodeMapping, Integer> taxRatesOccurs = new LinkedHashMap<>();

        taxRatesOccurs.put(TaxRateCodeMapping.H, 0);
        taxRatesOccurs.put(TaxRateCodeMapping.L, 0);
        taxRatesOccurs.put(TaxRateCodeMapping.Z, 0);

        return taxRatesOccurs;
    }

    public static int getNumberOfPresentTaxSubTotals(Map<TaxRateCodeMapping, TaxSubTotal> taxesSubTotalsMappings){

        return (int) taxesSubTotalsMappings.values().stream()
            .map(taxSubTotal -> taxSubTotal.getTaxableValueAtRate())
            .filter(taxableValueAtRate -> taxableValueAtRate.doubleValue() > 0d)
            .count();

    }

}
