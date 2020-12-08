package fi.project.ui.controllers;

import fi.project.domain.services.UserService;
import fi.project.domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


/**
 * This class has the functionality of register page.
 */
public class RegisterController {

    @FXML
    Button returnButton;
    @FXML
    Button signUpButton;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;

    private final UserService userService = new UserService();

    Alert invalidCredentialsAlert = new Alert(Alert.AlertType.ERROR);
    Alert userCreatedAlert = new Alert(Alert.AlertType.INFORMATION);

    /**
     * Sets scene to log in page when return button is clicked.
     *
     * @throws IOException if login.fxml is not found.
     */
    public void handleReturnButtonClick() throws IOException {
        Stage stage = (Stage) returnButton.getScene().getWindow();;
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(parent));
    }

    /**
     * Adds new user to database when sign up button is clicked
     * if valid credentials are given
     * @throws SQLException if method getUser throws exception.
     * @throws ClassNotFoundException if method getUser throws exception.
     */
    public void handleSignUpButtonClick() throws SQLException, ClassNotFoundException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean usernameIsNotUnique = userService.getUser(username) != null;

        invalidCredentialsAlert.setTitle("Error");
        invalidCredentialsAlert.setContentText("Please try again.");

        if (username.length() < 4 || password.length() < 6) {
            invalidCredentialsAlert.setHeaderText("Username or password too short.");
            invalidCredentialsAlert.showAndWait();
        } else if (usernameIsNotUnique) {
            invalidCredentialsAlert.setHeaderText("Username " + username + " is already taken.");
            invalidCredentialsAlert.showAndWait();
        } else {
            userService.create(new User(username, password));
            userCreatedAlert.setHeaderText("New user " + username + " created!");
            usernameField.clear();
            passwordField.clear();
            userCreatedAlert.showAndWait();
        }
    }
}
