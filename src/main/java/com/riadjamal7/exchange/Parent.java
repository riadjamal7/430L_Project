package com.riadjamal7.exchange;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class Parent implements Initializable, OnPageCompleteListener{
    public BorderPane borderPane;
    public Button transactionButton;
    public Button loginButton;
    public Button registerButton;
    public Button logoutButton;
    public Button marketButton;
    @Override
    public void onPageCompleted() {
        swapContent(Section.RATES);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateNavigation();
    }
    public void ratesSelected() {
        swapContent(Section.RATES);
    }
    public void transactionsSelected() {
        swapContent(Section.TRANSACTIONS);
    }
    public void loginSelected() {
        swapContent(Section.LOGIN);
    }
    public void registerSelected() {
        swapContent(Section.REGISTER);
    }
    public void logoutSelected() {
        Authentication.getInstance().deleteToken();
        swapContent(Section.RATES);
    }
    public void marketSelected() {
        swapContent(Section.MARKET);
    }
    private void swapContent(Section section) {
        try {
            URL url = getClass().getResource(section.getResource());
            FXMLLoader loader = new FXMLLoader(url);
            borderPane.setCenter(loader.load());
            if (section.doesComplete()) {
                ((PageCompleter)
                        loader.getController()).setOnPageCompleteListener(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        updateNavigation();
    }
    private void updateNavigation() {
        boolean authenticated = Authentication.getInstance().getToken() !=
                null;
        transactionButton.setManaged(authenticated);
        transactionButton.setVisible(authenticated);
        loginButton.setManaged(!authenticated);
        loginButton.setVisible(!authenticated);
        registerButton.setManaged(!authenticated);
        registerButton.setVisible(!authenticated);
        logoutButton.setManaged(authenticated);
        logoutButton.setVisible(authenticated);
    }
    private enum Section {
        RATES,
        TRANSACTIONS,
        LOGIN,
        REGISTER,
        MARKET;
        public boolean doesComplete() {
            return switch (this) {
                case LOGIN, REGISTER -> true;
                default -> false;
            };
        }

        public String getResource() {
            return switch (this) {
                case RATES ->
                        "/com/riadjamal7/exchange/rates/rates.fxml";
                case TRANSACTIONS ->
                        "/com/riadjamal7/exchange/transactions/transactions.fxml";
                case LOGIN ->
                        "/com/riadjamal7/exchange/login/login.fxml";
                case REGISTER ->
                        "/com/riadjamal7/exchange/register/register.fxml";
                case MARKET ->
                        "/com/riadjamal7/exchange/market/market.fxml";
                default -> null;
            };
        }

    }
}

