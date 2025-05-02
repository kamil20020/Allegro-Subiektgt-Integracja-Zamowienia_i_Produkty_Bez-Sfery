package pl.kamil_dywan.service;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.allegro.ProductApi;
import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProductResponse;
import pl.kamil_dywan.external.allegro.generated.offer_product.ProductOffer;
import pl.kamil_dywan.external.allegro.generated.offer_product.SellingMode;
import pl.kamil_dywan.external.subiektgt.own.product.*;
import pl.kamil_dywan.factory.ProductDetailedPriceFactory;
import pl.kamil_dywan.factory.AllegroProductOfferFactory;
import pl.kamil_dywan.file.write.EppFileWriter;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.mapper.ProductOfferMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductApi productApi;

    private static FileWriter<ProductRelatedData> subiektOrderFileWriter;

    static {

        List<String> headersNames = List.of("TOWARY", "CENNIK", "GRUPYTOWAROW", "CECHYTOWAROW", "DODATKOWETOWAROW", "TOWARYKODYCN", "TOWARYGRUPYJPKVAT");
        List<Integer> toWriteHeadersIndexes = List.of(0, 1);
        List<Integer> rowsLengths = List.of(43, 7);
        LinkedHashMap<String, Integer[]> writeIndexes = new LinkedHashMap<>();
        writeIndexes.put("TOWARY", new Integer[]{0, 1, 4, 11, 14});

        subiektOrderFileWriter = new EppFileWriter<>(headersNames, toWriteHeadersIndexes, rowsLengths, writeIndexes);
    }

    public ProductService(ProductApi productApi){

        this.productApi = productApi;
    }

    public OfferProductResponse getGeneralProductsPage(int offset, int limit){

        HttpResponse<String> gotResponse = productApi.getOffersProducts(offset, limit);

        return Api.extractBody(gotResponse, OfferProductResponse.class);
    }

    public List<ProductOffer> getDetailedProductsByIds(List<Long> productsIds){

        return productsIds.stream()
            .map(productId -> getDetailedProductById(productId))
            .collect(Collectors.toList());
    }

    public ProductOffer getDetailedProductById(Long id){

        HttpResponse<String> gotResponse = productApi.getProductOfferById(id);

        return Api.extractBody(gotResponse, ProductOffer.class);
    }

    public ProductOffer getDeliveryService(){

        return AllegroProductOfferFactory.createDeliveryProductOffer();
    }

    public void writeProductsToFile(List<ProductOffer> productsOffers, String filePath, ProductType productsTypes) throws IllegalStateException{

        List<Product> gotConvertedSubiektProducts = new ArrayList<>();
        List<ProductDetailedPrice> productsDetailedPrices = new ArrayList<>();

        ProductRelatedData productRelatedData = new ProductRelatedData(gotConvertedSubiektProducts, productsDetailedPrices);

        for(ProductOffer productOffer : productsOffers){

            appendProduct(productRelatedData, productOffer, productsTypes);
        }

        writeProductsToFile(productRelatedData, filePath);
    }

    private void appendProduct(ProductRelatedData productRelatedData, ProductOffer productOffer, ProductType productType){

        Product gotSubiektProduct = ProductOfferMapper.map(productOffer, productType);

        SellingMode sellingMode = productOffer.getSellingMode();
        BigDecimal unitPriceWithTax = sellingMode.getPrice().getAmount();

        ProductDetailedPrice productDetailedRetailPrice = ProductDetailedPriceFactory.create(
            gotSubiektProduct.getId(),
            gotSubiektProduct.getUnitPriceWithoutTax(),
            unitPriceWithTax
        );

        productRelatedData.products().add(gotSubiektProduct);
        productRelatedData.productPriceMappings().add(productDetailedRetailPrice);
    }

    private static void writeProductsToFile(ProductRelatedData productRelatedData, String filePath){

        try {
            subiektOrderFileWriter.save(filePath, productRelatedData);
        }
        catch (IOException | URISyntaxException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }
    }

}
