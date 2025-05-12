package pl.kamil_dywan.gui;

import pl.kamil_dywan.exception.UnloggedException;
import pl.kamil_dywan.external.allegro.generated.auth.GenerateDeviceCodeResponse;
import pl.kamil_dywan.service.AuthService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;

public class LoginGui {

    private JPanel mainPanel;
    private JButton loginButton;

    private String deviceCode;

    private final AuthService authService;
    private final Runnable handleSuccessAuth;

    public LoginGui(AuthService authService, Runnable handleSuccessAuth) {

        this.authService = authService;
        this.handleSuccessAuth = handleSuccessAuth;

        loginButton.addActionListener(e -> {

            if (!authService.doesUserPassedFirstLoginToApp()) {

                handleFirstLogin();
            } else if (deviceCode == null) {

                handleGenerateDeviceCodeAndVerificationUri();
            } else {

                handleLogin();
            }
        });
    }

    private void handleFirstLogin() {

        String gotUserPassword = JOptionPane.showInputDialog(
                mainPanel,
                "Proszę podać hasło do aplikacji",
                "Formularz logowania",
                JOptionPane.INFORMATION_MESSAGE
        );

        if (gotUserPassword == null) {
            return;
        }

        try {

            authService.initAllegroSecret(gotUserPassword);
        } catch (UnloggedException e) {

            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Wprowadzono niepoprawne hasło",
                    "Powiadomienie o błędzie",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        } catch (IllegalStateException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                mainPanel,
                "Wystąpił błąd podczas logowania do aplikacji",
                "Powiadomienie o błędzie",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
            mainPanel,
            "Zalogowano do aplikacji",
            "Powiadomienie",
            JOptionPane.INFORMATION_MESSAGE
        );

        loginButton.setText("Połącz");
    }

    private void handleGenerateDeviceCodeAndVerificationUri() {

        GenerateDeviceCodeResponse generateDeviceCodeResponse;

        try {
            generateDeviceCodeResponse = authService.generateDeviceCodeAndVerificationToAllegro();
        } catch (IllegalStateException e) {

            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Nie udało się rozpocząć procedury łączenia aplikacji z Allegro",
                    "Powiadomienie o błędzie",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String verificationUrlComplete = generateDeviceCodeResponse.getVerificationUriComplete();
        deviceCode = generateDeviceCodeResponse.getDeviceCode();

        handleVerificationUriComplete(verificationUrlComplete);
    }

    private void handleVerificationUriComplete(String verificationUrlComplete) {

        JOptionPane.showMessageDialog(
                mainPanel,
                """
                            Proszę o zaakceptowanie przyznania uprawnień aplikacji
                            do niektórych własnych danych w Allegro. Aplikacja
                            przekieruje do strony Allegro po naciśnięciu
                            przycisku OK
                        """,
                "Powiadomienie",
                JOptionPane.INFORMATION_MESSAGE
        );

        URI verificationUriComplete = URI.create(verificationUrlComplete);

        try {
            Desktop.getDesktop().browse(verificationUriComplete);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        loginButton.setText("Gotowe");
    }

    private void handleLogin() {

        try {
            authService.loginToAllegro(deviceCode);
        } catch (IllegalStateException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Prawdopodobnie nie zezwolono dostępu aplikacji do Allegro",
                    "Powiadomienie o błędzie",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        handleSuccessAuth.run();

        JOptionPane.showMessageDialog(
                mainPanel,
                "Pomyślnie połączono aplikację z Allegro",
                "Powiadomienie",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public JPanel getMainPanel() {

        return mainPanel;
    }
}
