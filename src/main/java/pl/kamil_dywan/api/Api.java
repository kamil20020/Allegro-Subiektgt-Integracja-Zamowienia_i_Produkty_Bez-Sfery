package pl.kamil_dywan.api;

import pl.kamil_dywan.file.read.JSONFileReader;
import pl.kamil_dywan.service.AppProperties;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class Api {

    protected String API_PREFIX;

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public Api(String subDomain, String laterPrefix){

        if(subDomain != null && !subDomain.isBlank()){
            subDomain += '.';
        }

        API_PREFIX =  "https://" + subDomain + getEnvApiHost() + laterPrefix;
    }

    public Api(String laterPrefix){

        API_PREFIX = "https://" + getEnvApiHost() + laterPrefix;
    }

    protected static String getQueryParamsPostFix(String... titlesAndParams){

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("?");

        for(int i = 0; i < titlesAndParams.length - 1; i += 2){

            if(i > 0){

                stringBuilder.append("&");
            }

            stringBuilder.append(titlesAndParams[i]);
            stringBuilder.append("=");
            stringBuilder.append(titlesAndParams[i + 1]);
        }

        return stringBuilder.toString();
    }

    private String getEnvApiHost(){

        return AppProperties.getProperty("allegro.api.host", String.class);
    }

    public HttpResponse<String> send(HttpRequest.Builder httpRequestBuilder) throws IllegalStateException{

        HttpRequest httpRequest = httpRequestBuilder.build();

        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }
    }

    public static <T> T extractBody(HttpResponse<String> httpResponse, Class<T> type){

        JSONFileReader<T> jsonFileReader = new JSONFileReader<T>(type);

        return jsonFileReader.loadFromStr(httpResponse.body());
    }
}
