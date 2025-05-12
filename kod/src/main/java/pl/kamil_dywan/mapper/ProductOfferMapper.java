package pl.kamil_dywan.mapper;

import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProduct;
import pl.kamil_dywan.external.allegro.generated.offer_product.ProductOffer;
import pl.kamil_dywan.external.allegro.generated.offer_product.SellingMode;
import pl.kamil_dywan.external.allegro.generated.offer_product.TaxSettings;
import pl.kamil_dywan.external.subiektgt.own.product.Product;
import pl.kamil_dywan.external.subiektgt.own.product.ProductType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface ProductOfferMapper {

    public static Product map(ProductOffer allegroProductOffer, ProductType productType){

        if(allegroProductOffer == null){

            return null;
        }

        TaxSettings taxSettings = allegroProductOffer.getTaxSettings();
        SellingMode sellingMode = allegroProductOffer.getSellingMode();

        BigDecimal taxRatePercentage = getTaxRatePercentage(taxSettings);
        BigDecimal taxRateValue = taxRatePercentage.multiply(BigDecimal.valueOf(0.01));

        BigDecimal unitPriceWithTax = sellingMode.getPrice().getAmount();

        BigDecimal unitPriceWithoutTax = unitPriceWithTax.divide(
            BigDecimal.ONE.add(taxRateValue),
            RoundingMode.HALF_UP
        );

        return Product.builder()
            .id(allegroProductOffer.getId().toString())
            .name(allegroProductOffer.getName())
            .type(productType)
            .unitPriceWithoutTax(unitPriceWithoutTax)
            .taxRatePercentage(taxRatePercentage)
            .build();
    }

    private static BigDecimal getTaxRatePercentage(TaxSettings taxSettings){

        if(taxSettings == null){

            return BigDecimal.valueOf(23);
        }

        return taxSettings.getTaxesFoCountries().get(0).getTaxRate();
    }
}
