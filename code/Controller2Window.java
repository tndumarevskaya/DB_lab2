package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Controller2Window {
    @FXML
    private TextField dbName;
    @FXML
    private ListView<String> myListView;
    @FXML
    private MultipleSelectionModel<String> dbSelect = null;

    private DatabaseAction databaseAction;

    public void setDatabaseAction(DatabaseAction databaseAction) throws SQLException {
        System.out.println(databaseAction);
        this.databaseAction = databaseAction;
        System.out.println(this.databaseAction);
        ResultSet resultSet = databaseAction.getSt().executeQuery("SHOW DATABASES;");
        ObservableList<String> list = FXCollections.observableArrayList();
        while (resultSet.next()) {
            if (!(resultSet.getString("Database").startsWith("mysql") || resultSet.getString("Database").endsWith("schema")))
                list.add(resultSet.getString("Database"));
        }
        myListView.setItems(list);
    }

    public void createDB(ActionEvent actionEvent) throws SQLException {
        if (dbName.getText().trim().length() > 0){
            try {
                databaseAction.createDB(dbName.getText());
                myListView.getItems().add(dbName.getText());
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void deleteDB(ActionEvent actionEvent) {
        try {
            String temp = myListView.getSelectionModel().getSelectedItem().toString();
            databaseAction.dropDB(temp);
            int index = myListView.getSelectionModel().getSelectedIndex();
            myListView.getItems().remove(index);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void useDB(ActionEvent actionEvent) throws IOException, SQLException {
        String temp = myListView.getSelectionModel().getSelectedItem().toString();
        System.out.println(temp);
        databaseAction.useDB(temp);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("thirdWindow.fxml"));
        Parent root = loader.load();
        Controller3Window controller3Window = loader.getController();
        controller3Window.setDatabaseAction(databaseAction);
        loader.setController(controller3Window);
        Scene home_page_scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    void initialize() throws SQLException {
    }
}
