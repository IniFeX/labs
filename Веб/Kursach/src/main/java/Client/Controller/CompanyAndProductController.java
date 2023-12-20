package Client.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientSocket;
import Client.DBClass.Companies;
import Client.DBClass.Products;
import Client.Enum.RequestType;
import Client.TCP.Request;
import Server.DBClass.Transactions;
import animation.Fade;
import animation.Shake;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CompanyAndProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Add;

    @FXML
    private Button Add1;

    @FXML
    private Button Delete;

    @FXML
    private Button Delete1;

    @FXML
    private Button EndEdit;

    @FXML
    private Button EndEdit1;

    @FXML
    private TextField IdEditField;

    @FXML
    private TextField IdEditField1;

    @FXML
    private Label InfoErrorText;

    @FXML
    private Label InfoErrorText1;

    @FXML
    private TableColumn<Companies, String> NameCompanyColumn;

    @FXML
    private TextField NameCompanyField;

    @FXML
    private TextField NameProduct;

    @FXML
    private TableColumn<Products, String> NameProductColumn;

    @FXML
    private TextField Price;

    @FXML
    private TableColumn<Products, String> PriceColumn;

    @FXML
    private Button Show;

    @FXML
    private Button Show1;

    @FXML
    private Button Update;

    @FXML
    private Button Update1;

    @FXML
    private Button backButton;

    @FXML
    private Text errorText;

    @FXML
    private Text errorText1;

    @FXML
    private TableColumn<Companies, String> idCompanyColumn;

    @FXML
    private TableColumn<Products, String> idProductColumn;

    @FXML
    private TableView<Companies> tableCompany;

    @FXML
    private TableView<Products> tableProduct;

    @FXML
    void initialize() {
        Show.setOnAction(actionEvent -> {
            try {
                showInfoCompany();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Show1.setOnAction(actionEvent -> {
            try {
                showInfoProduct();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Add.setOnAction(actionEvent -> {
            addInfoCompany();
        });
        Add1.setOnAction(actionEvent -> {
            addInfoProduct();
        });

        Update.setOnAction(actionEvent -> {
            System.out.println("РЕДАКТ НАЧАЛО");
            String idText = IdEditField.getText().trim();
            if(!idText.equals("")){
                try {
                    updateInfo(idText);
                    System.out.println("РЕДАКТ ВЫПОЛНЕН");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("РЕДАКТ НЕ ВЫПОЛНЕН");
                errorText.setText("Поле не должно быть пустым");
                Shake Error = new Shake(errorText);
                Fade errorTextFade = new Fade(errorText);
                errorTextFade.play();
                Error.playAnim();

            }
        });
        Update1.setOnAction(actionEvent -> {
            System.out.println("РЕДАКТ НАЧАЛО");
            String idText = IdEditField1.getText().trim();
            if(!idText.equals("")){
                try {
                    updateInfoProduct(idText);
                    System.out.println("РЕДАКТ ВЫПОЛНЕН");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("РЕДАКТ НЕ ВЫПОЛНЕН");
                errorText1.setText("Поле не должно быть пустым");
                Shake Error = new Shake(errorText1);
                Fade errorTextFade = new Fade(errorText1);
                errorTextFade.play();
                Error.playAnim();

            }
        });


        Delete.setOnAction(actionEvent -> {
            String idText = IdEditField.getText().trim();
            if(!idText.equals("")){
                try {
                    deleteInfoCompany(idText);
                    System.out.println("УДАЛ ВЫПОЛНЕН");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("УДАЛ НЕ ВЫПОЛНЕН");
                errorText.setText("Поле не должно быть пустым");
                Shake Error = new Shake(errorText);
                Fade errorTextFade = new Fade(errorText);
                errorTextFade.play();
                Error.playAnim();
            }

        });
        Delete1.setOnAction(actionEvent -> {
            String idText = IdEditField1.getText().trim();
            if(!idText.equals("")){
                try {
                    deleteInfoProduct(idText);
                    System.out.println("УДАЛ ВЫПОЛНЕН");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("УДАЛ НЕ ВЫПОЛНЕН");
                errorText1.setText("Поле не должно быть пустым");
                Shake Error = new Shake(errorText1);
                Fade errorTextFade = new Fade(errorText1);
                errorTextFade.play();
                Error.playAnim();
            }

        });


        backButton.setOnAction(actionEvent -> {
            openNewScene("/app.fxml");
        });
    }

    private void updateInfoProduct(String idText) throws IOException{
        Request request = new Request();
        Products products = new Products();
        products.setIdProduct(idText);

        request.setRequestType(RequestType.UPDATE_PRODUCT);
        request.setRequestMessage((new Gson()).toJson(products));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        EndEdit1.setVisible(true);
        EndEdit1.setOnAction(actionEvent -> {
            String name = NameProduct.getText().trim();
            String price = Price.getText().trim();
            Products prod;
            if(!name.equals("") && !price.equals("")){
                prod = new Products(idText, name, price);
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
                ClientSocket.getInstance().getOut().flush();

                ClientSocket.getInstance().getOut().println((new Gson()).toJson(prod));
                ClientSocket.getInstance().getOut().flush();
            }
            else{
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("false"));
                ClientSocket.getInstance().getOut().flush();
                System.out.println("РЕДАКТ ВАЩЕ НЕ ВЫПОЛНЕН");

                errorText1.setText("Поля не должны быть пустыми");
                Shake Error = new Shake(errorText1);
                Fade errorTextFade = new Fade(errorText1);
                errorTextFade.play();
                Error.playAnim();

            }
        });

    }

    private void updateInfo(String idText) throws IOException{
        Request request = new Request();
        Companies companies = new Companies();
        companies.setIdCompany(idText);

        request.setRequestType(RequestType.UPDATE_COMPANY);
        request.setRequestMessage((new Gson()).toJson(companies));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        EndEdit.setVisible(true);
        EndEdit.setOnAction(actionEvent -> {
            String name = NameCompanyField.getText().trim();
            Companies comp;
            if(!name.equals("")){
                comp = new Companies(idText, name);
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
                ClientSocket.getInstance().getOut().flush();

                ClientSocket.getInstance().getOut().println((new Gson()).toJson(comp));
                ClientSocket.getInstance().getOut().flush();
            }
            else{
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("false"));
                ClientSocket.getInstance().getOut().flush();
                System.out.println("РЕДАКТ ВАЩЕ НЕ ВЫПОЛНЕН");

                errorText.setText("Поля не должны быть пустыми");
                Shake Error = new Shake(errorText);
                Fade errorTextFade = new Fade(errorText);
                errorTextFade.play();
                Error.playAnim();

            }
        });
    }

    private void deleteInfoCompany(String idText)throws IOException{
        Request request = new Request();
        Companies companies = new Companies();
        companies.setIdCompany(idText);
        request.setRequestType(RequestType.DELETE_COMPANY);
        request.setRequestMessage((new Gson()).toJson(companies));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

    }
    private void deleteInfoProduct(String idText)throws IOException{
        Request request = new Request();
        Products products = new Products();
        products.setIdProduct(idText);
        request.setRequestType(RequestType.DELETE_PRODUCT);
        request.setRequestMessage((new Gson()).toJson(products));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

    }


    private void addInfoCompany() {
        Request request = new Request();
        String name = NameCompanyField.getText().trim();

        request.setRequestType(RequestType.ADD_COMPANY);
        request.setRequestMessage((new Gson()).toJson("plz"));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        if(!name.equals("")){
            Companies companies = new Companies(null, name);
            ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
            ClientSocket.getInstance().getOut().flush();

            ClientSocket.getInstance().getOut().println((new Gson()).toJson(companies));
            ClientSocket.getInstance().getOut().flush();

        }else{
            ClientSocket.getInstance().getOut().println((new Gson()).toJson("false"));
            ClientSocket.getInstance().getOut().flush();
            System.out.println("add ВАЩЕ НЕ ВЫПОЛНЕН");

            errorText.setText("Поля не должны быть пустыми");
            Shake Error = new Shake(errorText);
            Fade errorTextFade = new Fade(errorText);
            errorTextFade.play();
            Error.playAnim();

        }
    }

    private void addInfoProduct() {
        Request request = new Request();
        String name = NameProduct.getText().trim();
        String price = Price.getText().trim();

        request.setRequestType(RequestType.ADD_PRODUCT);
        request.setRequestMessage((new Gson()).toJson("plz"));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        if(!name.equals("") && !price.equals("")){
            Products products = new Products(null, name, price);
            ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
            ClientSocket.getInstance().getOut().flush();

            ClientSocket.getInstance().getOut().println((new Gson()).toJson(products));
            ClientSocket.getInstance().getOut().flush();

        }else{
            ClientSocket.getInstance().getOut().println((new Gson()).toJson("false"));
            ClientSocket.getInstance().getOut().flush();
            System.out.println("add ВАЩЕ НЕ ВЫПОЛНЕН");

            errorText1.setText("Поля не должны быть пустыми");
            Shake Error = new Shake(errorText1);
            Fade errorTextFade = new Fade(errorText1);
            errorTextFade.play();
            Error.playAnim();

        }
    }


    private void showInfoCompany() throws IOException{
        ArrayList<Companies> companies = new ArrayList<>();
        String type = "SHOW_COMPANY";
        Request request = new Request();
        request.setRequestType(RequestType.SHOW_COMPANY);
        request.setRequestMessage((new Gson()).toJson(type));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String js = ClientSocket.getInstance().getInStream().readLine();
        companies = (new Gson()).fromJson(js, new TypeToken<ArrayList<Companies>>() {}.getType());

        ObservableList<Companies> compList = FXCollections.observableArrayList(companies);
        idCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("idCompany"));
        NameCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        tableCompany.setItems(compList);
    }
    private void showInfoProduct() throws IOException{
        ArrayList<Products> products = new ArrayList<>();
        String type = "SHOW_PRODUCT";
        Request request = new Request();
        request.setRequestType(RequestType.SHOW_PRODUCT);
        request.setRequestMessage((new Gson()).toJson(type));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String js = ClientSocket.getInstance().getInStream().readLine();
        products = (new Gson()).fromJson(js, new TypeToken<ArrayList<Products>>() {}.getType());

        ObservableList<Products> prodList = FXCollections.observableArrayList(products);
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        NameProductColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        tableProduct.setItems(prodList);
    }
    public void openNewScene(String scene){
        backButton.getScene().getWindow().hide();
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
