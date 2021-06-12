package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Statement;

public class Main extends Application {

    private DatabaseAction databaseAction;
    private BorderPane root;
    private Stage stage;
    public Statement st;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("firstWindow.fxml"));
        this.stage = stage;
        databaseAction = new DatabaseAction();
        primaryStage.setTitle("DATABASE");
        //initRootView();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /*public void initRootView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("firstWindow.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
