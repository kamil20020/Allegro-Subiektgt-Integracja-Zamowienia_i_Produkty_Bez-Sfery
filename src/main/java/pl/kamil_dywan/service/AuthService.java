package pl.kamil_dywan.service;

import com.thoughtworks.xstream.core.util.Base64Encoder;
import pl.kamil_dywan.api.Api;
import pl.kamil_dywan.api.BasicAuthApi;
import pl.kamil_dywan.api.BearerAuthApi;
import pl.kamil_dywan.api.allegro.LoginApi;
import pl.kamil_dywan.exception.UnloggedException;
import pl.kamil_dywan.external.allegro.generated.auth.AccessTokenResponse;
import pl.kamil_dywan.external.allegro.generated.auth.GenerateDeviceCodeResponse;
import pl.kamil_dywan.external.allegro.own.EncryptedAllegroLoginDetails;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AuthService {

    private final LoginApi loginApi;

    private static EncryptedAllegroLoginDetails encryptedAllegroLoginDetails;

    private static final FileReader<EncryptedAllegroLoginDetails> allegroLoginDetailsFileReader = new JSONFileReader<>(EncryptedAllegroLoginDetails.class);

    public AuthService(LoginApi loginApi){

        this.loginApi = loginApi;

        init();
    }

    public boolean isUserLogged(){

        return BearerAuthApi.isUserLogged();
    }

    public boolean doesUserPassedFirstLoginToApp(){

        return SecureStorage.doesExist(BasicAuthApi.ALLEGRO_SECRET_POSTFIX);
    }

    private void init() throws IllegalStateException{

        if(doesUserPassedFirstLoginToApp()){
            return;
        }

        try {
            encryptedAllegroLoginDetails = allegroLoginDetailsFileReader.loadFromOutside("./auth-data.json");
        }
        catch (URISyntaxException | IOException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }

        new File("./auth-data.json").delete();
    }

    public void initAllegroSecret(String gotPassword) throws IllegalStateException, UnloggedException{

        if(gotPassword == null){

            throw new IllegalStateException("User is already logged in");
        }

        if(doesUserPassedFirstLoginToApp()){

            throw new IllegalStateException("The user was already logged in to the app for the first time");
        }

        Base64.Encoder base64Encoder = Base64.getEncoder();

        byte[] gotPasswordBytes = gotPassword.getBytes();

        byte[] targetGotPasswordBytes = new byte[16];

        System.arraycopy(gotPasswordBytes, 0, targetGotPasswordBytes, 0, Math.min(gotPasswordBytes.length, targetGotPasswordBytes.length));

        byte[] base64EncodedPassword = base64Encoder.encode(targetGotPasswordBytes);

        String base64EncryptedAes = encryptedAllegroLoginDetails.getKey();

        String gotDecryptedAes;

        try {
            gotDecryptedAes = SecurityService.decryptAes(base64EncodedPassword, base64EncryptedAes);
        }
        catch (BadPaddingException e){

            throw new UnloggedException();
        }
        catch (Exception e) {
            e.printStackTrace();

            throw new IllegalStateException("Could not decrypt with user password");
        }

        byte[] gotDecryptedAesHashArr;

        try {
            gotDecryptedAesHashArr = SecurityService.hashSha(gotDecryptedAes);
        }
        catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

            throw new IllegalStateException("Did not found hash algorithm");
        }

        String gotDecryptedAesHash = new String(gotDecryptedAesHashArr);
        String expectedAesHash = encryptedAllegroLoginDetails.getKeyHash();

        if(!gotDecryptedAesHash.equals(expectedAesHash)){

            throw new UnloggedException();
        }

        String base64EncryptedSecret = encryptedAllegroLoginDetails.getSecret();

        gotDecryptedAes = Base64.getEncoder().encodeToString(gotDecryptedAes.getBytes());

        String gotAllegroSecret;

        try {
            gotAllegroSecret = SecurityService.decryptAes(gotDecryptedAes.getBytes(), base64EncryptedSecret);
        }
        catch (Exception e) {

            e.printStackTrace();

            throw new IllegalStateException("Could not decrypt allegro secret");
        }

        SecureStorage.saveCredentials(BasicAuthApi.ALLEGRO_SECRET_POSTFIX, gotAllegroSecret);

        BasicAuthApi.init();
    }

    public GenerateDeviceCodeResponse generateDeviceCodeAndVerificationToAllegro() throws IllegalStateException{

        HttpResponse<String> deviceCodeResponse = loginApi.generateDeviceCodeAndVerification();

        if(deviceCodeResponse.statusCode() != 200){
            throw new IllegalStateException("Could not generate device code");
        }

        return Api.extractBody(deviceCodeResponse, GenerateDeviceCodeResponse.class);
    }

    public void loginToAllegro(String deviceCode) throws IllegalStateException{

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
