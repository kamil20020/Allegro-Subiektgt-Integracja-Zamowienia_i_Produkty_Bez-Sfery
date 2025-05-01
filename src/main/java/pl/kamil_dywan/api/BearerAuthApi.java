package pl.kamil_dywan.api;

import pl.kamil_dywan.exception.UnloggedException;
import pl.kamil_dywan.external.allegro.generated.auth.AccessTokenResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;

public class BearerAuthApi extends Api{

    private static String accessToken;
    private static String refreshToken;
    private static String bearerAuthContent;
    private static Integer refreshTokenExpiresIn;
    private static Function<String, HttpResponse<String>> refreshAccessToken;

    public BearerAuthApi(String subDomain, String laterPrefix){

        super(subDomain, laterPrefix);
    }

    public BearerAuthApi(String laterPrefix){

        this("", laterPrefix);
    }

    public static void init(String accessToken1, String refreshToken1, Function<String, HttpResponse<String>> refreshAccessToken1){

        accessToken = accessToken1;
        refreshToken = refreshToken1;
        refreshAccessToken = refreshAccessToken1;

        bearerAuthContent = getBearerAuthContent(accessToken);
    }

    @Override
    public HttpResponse<String> send(HttpRequest.Builder httpRequestBuilder) throws IllegalStateException, UnloggedException {

        httpRequestBuilder
            .header("Authorization", bearerAuthContent);

        HttpResponse<String> gotResponse = super.send(httpRequestBuilder);

        if (gotResponse.statusCode() == 401) {

            if(!handleRefreshAccessToken()){
                throw new UnloggedException();
            }
        }
        else{
            gotResponse = super.send(httpRequestBuilder);
        }

        return gotResponse;
    }

    private boolean handleRefreshAccessToken() throws IllegalStateException{

        HttpResponse<String> gotResponse = refreshAccessToken.apply(refreshToken);

        if(gotResponse.statusCode() == 401){

            accessToken = null;
            refreshToken = null;

            return false;
        }

        AccessTokenResponse accessTokenResponse = Api.extractBody(gotResponse, AccessTokenResponse.class);

        accessToken = accessTokenResponse.getAccessToken();
        refreshToken = accessTokenResponse.getRefreshToken();
        bearerAuthContent = getBearerAuthContent(accessToken);

        return true;
    }

    private static String getBearerAuthContent(String token){

        return "Bearer " + token;
    }

    public static boolean isUserLogged(){

        return accessToken != null;
    }
}
