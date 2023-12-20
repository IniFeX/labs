package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ClientSocket.getInstance().getSocket();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("h");
        stage.setScene(scene);
        stage.show();
        //clientApp.start();
    }

    public static void main(String[] args) {
        launch();
    }
}