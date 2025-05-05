package pl.kamil_dywan.service;

import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.BearerAuthApi;
import pl.kamil_dywan.api.allegro.LoginApi;
import pl.kamil_dywan.external.allegro.generated.auth.AccessTokenResponse;
import pl.kamil_dywan.external.allegro.generated.auth.GenerateDeviceCodeResponse;

import java.net.http.HttpResponse;

public class AuthService {

    private final LoginApi loginApi;

    public AuthService(LoginApi loginApi){

        this.loginApi = loginApi;
    }

    public boolean isUserLogged(){

        return BearerAuthApi.isUserLogged();
    }

    public GenerateDeviceCodeResponse generateDeviceCodeAndVerification() throws IllegalStateException{

        HttpResponse<String> deviceCodeResponse = loginApi.generateDeviceCodeAndVerification();

        if(deviceCodeResponse.statusCode() != 200){
            throw new IllegalStateException("Could not generate device code");
        }

        return Api.extractBody(deviceCodeResponse, GenerateDeviceCodeResponse.class);
    }

    public void login(String deviceCode) throws IllegalStateException{

        HttpResponse<String> accessTokenResponse = loginApi.generateAccessToken(deviceCode);

        if(accessTokenResponse.statusCode() != 200){
            throw new IllegalStateException("User did not authorized the device code");
        }

        AccessTokenResponse accessTokenContent = Api.extractBody(accessTokenResponse, AccessTokenResponse.class);

        String accessToken = accessTokenContent.getAccessToken();
        String refreshToken = accessTokenContent.getRefreshToken();

        BearerAuthApi.saveAuthData(accessToken, refreshToken);
    }

    public void logout(){

        BearerAuthApi.logout();
    }
}
