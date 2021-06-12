package sample;

import com.mysql.cj.jdbc.Driver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Controller {
    @FXML
    public TextField user;
    @FXML
    public PasswordField password;
    @FXML
    public Button button_connect;
    @FXML
    private Label invalid_label;


    private DatabaseAction databaseAction;

    @FXML
    public void switchToScene1(javafx.event.ActionEvent event) throws IOException, SQLException {
        System.out.println("You clicked me!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("secondWindow.fxml"));
            Parent home_page_parent = loader.load();
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (isValidCredentials()) {
                Controller2Window controller2Window = loader.getController();
                controller2Window.setDatabaseAction(databaseAction);
                app_stage.hide();
                app_stage.setScene(home_page_scene);
                app_stage.show();
            } else {
                user.clear();
                password.clear();
                invalid_label.setText("Sorry, invalid credentials");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidCredentials() throws SQLException {
        boolean let_in = false;
        System.out.println("SELECT * FROM Users WHERE USERNAME= " + "'" + user.getText() + "'"
                + " AND PASSWORD= " + "'" + password.getText() + "';");
        try {
            DatabaseAction databaseA = new DatabaseAction();
            databaseA.connection(user.getText(), password.getText());
            databaseAction = databaseA;
            let_in = true;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return let_in;
    }
}
