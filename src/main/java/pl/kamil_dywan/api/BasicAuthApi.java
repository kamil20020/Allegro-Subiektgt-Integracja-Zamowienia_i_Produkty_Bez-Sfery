package pl.kamil_dywan.api;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.mapper.Base64Mapper;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BasicAuthApi extends Api {

    private String authHeaderContent;

    public BasicAuthApi(String username, String password,  String subDomain, String laterPrefix) {
        super(subDomain, laterPrefix);

        authHeaderContent = "Basic " + Base64Mapper.mapToBase64(username + ":" + password);
    }

    public BasicAuthApi(String username, String password, String laterPrefix) {
        this(username, password, "", laterPrefix);
    }

    @Override
    public HttpResponse<String> send(HttpRequest.Builder httpRequestBuilder) throws IllegalStateException {

        httpRequestBuilder
            .header("Authorization", authHeaderContent);

        return super.send(httpRequestBuilder);
    }
}
