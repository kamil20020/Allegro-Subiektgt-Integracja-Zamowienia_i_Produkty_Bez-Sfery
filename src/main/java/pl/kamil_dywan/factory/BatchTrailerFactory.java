package pl.kamil_dywan.factory;

import pl.kamil_dywan.external.subiektgt.generated.BatchTrailer;
import pl.kamil_dywan.external.subiektgt.generated.Currency;
import pl.kamil_dywan.external.subiektgt.generated.CurrencyHolder;
import pl.kamil_dywan.external.subiektgt.own.Code;

public class BatchTrailerFactory {

    private BatchTrailerFactory(){


    }

    public static BatchTrailer create(Code currencyCode){

        return BatchTrailer.builder()
            .itemCurrency(new CurrencyHolder(new Currency(currencyCode)))
            .build();
    }
}
