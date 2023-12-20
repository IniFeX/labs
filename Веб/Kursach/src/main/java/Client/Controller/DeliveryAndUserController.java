package Client.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientSocket;
import Client.DBClass.Companies;
import Client.DBClass.Delivery;
import Client.DBClass.User;
import Client.Enum.RequestType;
import Client.TCP.Request;
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

public class DeliveryAndUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Add;

    @FXML
    private Button Add1;


    @FXML
    private TextField AdressField;

    @FXML
    private Button Delete;

    @FXML
    private Button Delete1;

    @FXML
    private Button EndEdit;

    @FXML
    private Button EndEdit1;


    @FXML
    private TextField GenderField;

    @FXML
    private TextField IdEditField;

    @FXML
    private TextField IdEditField1;

    @FXML
    private Label InfoErrorText;

    @FXML
    private Label InfoErrorText1;


    @FXML
    private TextField LoginField;


    @FXML
    private TextField PasswordField;


    @FXML
    private TextField RoleField;

    @FXML
    private Button Show;

    @FXML
    private Button Show1;


    @FXML
    private TextField StatusField;

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
    private TextField idCompanyField;
    @FXML
    private TextField idTransactionField;
    // DELIVERY
    @FXML
    private TableColumn<Delivery, String> AdressColumn;
    @FXML
    private TableColumn<Delivery, String> idDelivery;
    @FXML
    private TableColumn<Delivery, String> idTransactionColumn;
    @FXML
    private TableColumn<Delivery, String> StatusColumn;
    @FXML
    private TableView<Delivery> tableDelivery;
    // PRODUCT
    @FXML
    private TableColumn<User, String> GenderColumn;
    @FXML
    private TableColumn<User, String> LoginColumn;
    @FXML
    private TableColumn<User, String> RoleColumn;
    @FXML
    private TableColumn<User, String> PasswordColumn;
    @FXML
    private TableColumn<User, String> idCompanyColumn;
    @FXML
    private TableColumn<User, String> idUser;
    @FXML
    private TableView<User> tableUser;

    @FXML
    void initialize() {
        Show.setOnAction(actionEvent -> {
            try {
                showInfoDelivery();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Show1.setOnAction(actionEvent -> {
            try {
                showInfoUser();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Add.setOnAction(actionEvent -> {
            addInfoDelivery();
        });
        Add1.setOnAction(actionEvent -> {
            addInfoUser();
        });

        Update.setOnAction(actionEvent -> {
            System.out.println("РЕДАКТ НАЧАЛО");
            String idText = IdEditField.getText().trim();
            if(!idText.equals("")){
                try {
                    updateInfoDelivery(idText);
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
                    updateInfoUser(idText);
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
                    deleteInfoDelivery(idText);
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
                    deleteInfoUser(idText);
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


    private void showInfoUser() throws IOException {
        ArrayList<User> user = new ArrayList<>();
        String type = "SHOW_USER";
        Request request = new Request();
        request.setRequestType(RequestType.SHOW_USER);
        request.setRequestMessage((new Gson()).toJson(type));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String js = ClientSocket.getInstance().getInStream().readLine();
        user = (new Gson()).fromJson(js, new TypeToken<ArrayList<User>>() {}.getType());

        ObservableList<User> userList = FXCollections.observableArrayList(user);
        idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        LoginColumn.setCellValueFactory(new PropertyValueFactory<>("Login"));
        PasswordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
        RoleColumn.setCellValueFactory(new PropertyValueFactory<>("Role"));
        GenderColumn.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        idCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("idCompany"));

        tableUser.setItems(userList);

    }
    private void showInfoDelivery() throws IOException {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        String type = "SHOW_DELIVERY";
        Request request = new Request();
        request.setRequestType(RequestType.SHOW_DELIVERY);
        request.setRequestMessage((new Gson()).toJson(type));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String js = ClientSocket.getInstance().getInStream().readLine();
        deliveries = (new Gson()).fromJson(js, new TypeToken<ArrayList<Delivery>>() {}.getType());

        ObservableList<Delivery> devList = FXCollections.observableArrayList(deliveries);
        idDelivery.setCellValueFactory(new PropertyValueFactory<>("idDelivery"));
        AdressColumn.setCellValueFactory(new PropertyValueFactory<>("DeliveryAdress"));
        idTransactionColumn.setCellValueFactory(new PropertyValueFactory<>("idTransaction"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<>("DeliveryStatus"));

        tableDelivery.setItems(devList);
    }


    private void addInfoDelivery() {
        Request request = new Request();
        String addres = AdressField.getText().trim();
        String status = StatusField.getText().trim();
        String idTransaction = idTransactionField.getText().trim();

        request.setRequestType(RequestType.ADD_DELIVERY);
        request.setRequestMessage((new Gson()).toJson("plDz"));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        if(!addres.equals("") && !status.equals("") && !idTransaction.equals("")){
            Delivery delivery = new Delivery(null, idTransaction, addres, status);
            ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
            ClientSocket.getInstance().getOut().flush();

            ClientSocket.getInstance().getOut().println((new Gson()).toJson(delivery));
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
    private void addInfoUser() {
        Request request = new Request();
        String login = LoginField.getText().trim();
        String password = PasswordField.getText().trim();
        String role = RoleField.getText().trim();
        String idCompany = idCompanyField.getText().trim();
        String gender = GenderField.getText().trim();

        request.setRequestType(RequestType.ADD_USER);
        request.setRequestMessage((new Gson()).toJson("plDz"));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        if(!login.equals("") && !password.equals("") && !role.equals("") && !idCompany.equals("") && !gender.equals("")){
            User user = new User(null, login, password, gender, role, idCompany);
            ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
            ClientSocket.getInstance().getOut().flush();

            ClientSocket.getInstance().getOut().println((new Gson()).toJson(user));
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


    private void updateInfoDelivery(String idText) throws IOException {
        Request request = new Request();
        Delivery delivery = new Delivery();
        delivery.setIdDelivery(idText);

        request.setRequestType(RequestType.UPDATE_DELIVERY);
        request.setRequestMessage((new Gson()).toJson(delivery));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        EndEdit.setVisible(true);
        EndEdit.setOnAction(actionEvent -> {
            String idTransaction = idTransactionField.getText().trim();
            String address = AdressField.getText().trim();
            String status = StatusField.getText().trim();

            Delivery deli;
            if(!idTransaction.equals("") && !address.equals("") && !status.equals("")){
                deli = new Delivery(idText, idTransaction, address, status);
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
                ClientSocket.getInstance().getOut().flush();

                ClientSocket.getInstance().getOut().println((new Gson()).toJson(deli));
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
    private void updateInfoUser(String idText) throws IOException {
        Request request = new Request();
        User user = new User();
        user.setIdUser(idText);

        request.setRequestType(RequestType.UPDATE_USER);
        request.setRequestMessage((new Gson()).toJson(user));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        EndEdit1.setVisible(true);
        EndEdit1.setOnAction(actionEvent -> {
            String login = LoginField.getText().trim();
            String password = PasswordField.getText().trim();
            String role = RoleField.getText().trim();
            String idCompany = idCompanyField.getText().trim();
            String gender = GenderField.getText().trim();

            User us;
            if(!login.equals("") && !password.equals("") && !role.equals("") && !idCompany.equals("") && !gender.equals("")){
                us = new User(idText, login, password, gender, role, idCompany);
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
                ClientSocket.getInstance().getOut().flush();

                ClientSocket.getInstance().getOut().println((new Gson()).toJson(us));
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


    private void deleteInfoDelivery(String idText) throws IOException {
        Request request = new Request();
        Delivery delivery = new Delivery();
        delivery.setIdDelivery(idText);
        request.setRequestType(RequestType.DELETE_DELIVERY);
        request.setRequestMessage((new Gson()).toJson(delivery));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

    }
    private void deleteInfoUser(String idText) throws IOException{
        Request request = new Request();
        User user = new User();
        user.setIdUser(idText);
        request.setRequestType(RequestType.DELETE_USER);
        request.setRequestMessage((new Gson()).toJson(user));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();
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
