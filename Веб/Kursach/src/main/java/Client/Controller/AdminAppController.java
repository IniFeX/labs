package Client.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminAppController {

    @FXML
    private ImageView imageButtonHome;

    @FXML
    private Button BackButton;

    @FXML
    private Button CompanysButton;

    @FXML
    private Button DeliveryButton;

    @FXML
    private Button ImportExportButton;

    @FXML
    private Button ProductsButton;
    @FXML
    private Button Tak;
    @FXML
    private ImageView nah;
    @FXML
    private Button another;
    @FXML
    private ImageView nah1;
    @FXML
    private Button UserButton;
    boolean isnah = false;
    boolean isnah1 = false;

    @FXML
    void initialize() {
        Tak.setOnAction(actionEvent -> {
            nah1.setVisible(false);
            if(!isnah){
                nah.setVisible(true);
                isnah = true;
            }
            else {
                nah.setVisible(false);
                isnah = false;
            }
            another.setVisible(true);
        });
        another.setOnAction(actionEvent -> {
            nah.setVisible(false);
            if(!isnah1){
                nah1.setVisible(true);
                isnah1 = true;
            }
            else {
                nah1.setVisible(false);
                isnah1 = false;
            }
        });
        BackButton.setOnAction(actionEvent -> {
            openNewScene("/home.fxml");
        });
        ImportExportButton.setOnAction(actionEvent -> {
            openNewScene("/ImportExport.fxml");
        });
        CompanysButton.setOnAction(actionEvent -> {
            openNewScene("/companies.fxml");
        });
        DeliveryButton.setOnAction(actionEvent -> {
            openNewScene("/delivery.fxml");
        });



    }
    public void openNewScene(String scene){
        BackButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(scene));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        //
        stage.show();
        //
    }

}
