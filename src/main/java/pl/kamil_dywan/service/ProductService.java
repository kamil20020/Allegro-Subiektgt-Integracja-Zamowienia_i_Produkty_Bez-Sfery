package pl.kamil_dywan.service;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.allegro.ProductApi;
import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProduct;
import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProductResponse;
import pl.kamil_dywan.external.subiektgt.own.product.ProductRelatedData;
import pl.kamil_dywan.file.write.EppFileWriter;
import pl.kamil_dywan.file.write.FileWriter;
import pl.kamil_dywan.mapper.AllegroLineItemMapper;

import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.List;

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

    public OfferProductResponse getPage(int offset, int limit){

        HttpResponse<String> gotResponse = productApi.getOffersProducts(offset, limit);

        return Api.extractBody(gotResponse, OfferProductResponse.class);
    }

    public void writeProductsToFile(List<OfferProduct> offerProducts, String filePath){

//        subiektOrderFileWriter.save("./product-output.epp", productRelatedData);
    }
}
