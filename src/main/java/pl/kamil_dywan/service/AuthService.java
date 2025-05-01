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

    public GenerateDeviceCodeResponse generateDeviceCodeAndVerification() {

        HttpResponse<String> deviceCodeResponse = loginApi.generateDeviceCodeAndVerification();

        return Api.extractBody(deviceCodeResponse, GenerateDeviceCodeResponse.class);
    }

    public void login(String deviceCode){

        HttpResponse<String> accessTokenResponse = loginApi.generateAccessToken(deviceCode);
        AccessTokenResponse accessTokenContent = Api.extractBody(accessTokenResponse, AccessTokenResponse.class);

        String accessToken = accessTokenContent.getAccessToken();
        String refreshToken = accessTokenContent.getRefreshToken();

        BearerAuthApi.saveAuthData(accessToken, refreshToken);
    }

    public void logout(){

        BearerAuthApi.logout();
    }
}
