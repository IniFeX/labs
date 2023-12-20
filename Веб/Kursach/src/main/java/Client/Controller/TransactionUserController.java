package Client.Controller;

import Client.ClientSocket;
import Client.DBClass.Products;
import Client.DBClass.User;
import Client.Enum.RequestType;
import Client.TCP.Request;
import Client.IEClass;
import Server.DBClass.Companies;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TransactionUserController {
    User user;
    private String nameCompany;
    public void setData(User user, String name){
        this.user = user;
        this.nameCompany = name;
        System.out.println("company controller: " + user.getIdCompany());
//        getInfo(user);
//        System.out.println(this.user.getIdUser());
//        System.out.println(this.user.getLogin());
//        System.out.println(this.user.getGender());
//        System.out.println(this.user.getRole());
//        System.out.println(this.user.getIdCompany());
//        System.out.println(this.user.getPassword());
//
    }

    @FXML
    private Button Add;
    @FXML
    private TextField TypeField;
    @FXML
    private TextField IdEditField;

    @FXML
    private TextField NameCompanyField;

    @FXML
    private TextField NameProductField;
    @FXML
    private TextField DateField;
    @FXML
    private TextField AmountField;
    @FXML
    private TableColumn<IEClass, String> Amount;

    @FXML
    private TableColumn<IEClass, String> Company;

    @FXML
    private TableColumn<IEClass, String> Date;
    @FXML
    private Label InfoErrorText;
    @FXML
    private Button Delete;

    @FXML
    private TableColumn<IEClass, String> Product;

    @FXML
    private Button Show;

    @FXML
    private TableColumn<IEClass, String> Type;
    @FXML
    private Button EndEdit;
    @FXML
    private Button Update;

    @FXML
    private Button backButton;

    @FXML
    private Text errorText;
    @FXML
    private TableView<IEClass> table;
    @FXML
    private TableColumn<IEClass, String> idTransaction;

    @FXML
    void initialize() {
        //errorText = new Text();
        Show.setOnAction(actionEvent -> {
            try {
                showInfo();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Add.setOnAction(actionEvent -> {
            addInfo();
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

        Delete.setOnAction(actionEvent -> {
            String idText = IdEditField.getText().trim();
            if(!idText.equals("")){
                try {
                    deleteInfo(idText);
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

        backButton.setOnAction(actionEvent -> {
            openNewSceneUser("/userApp.fxml", this.user);
        });

    }

    private void addInfo() {
        Request request = new Request();
        String nameProduct = NameProductField.getText().trim();
        String nameCompany = this.nameCompany;
        String amount = AmountField.getText().trim();
        String date = DateField.getText().trim();
        String type = TypeField.getText().trim();
//        IEClass ieClass = new IEClass(null,nameProduct, nameCompany, amount, date, type);
        request.setRequestType(RequestType.ADD_TRANSACTIONS);
        request.setRequestMessage((new Gson()).toJson("ieClass"));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();
        IEClass ieClass;
        if (!nameProduct.equals("") && !nameCompany.equals("") && !amount.equals("") && !date.equals("") && !type.equals("")) {
            ieClass = new IEClass(null, nameProduct, nameCompany, amount, date, type);
            ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
            ClientSocket.getInstance().getOut().flush();

            ClientSocket.getInstance().getOut().println((new Gson()).toJson(ieClass));
            ClientSocket.getInstance().getOut().flush();
        } else {
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
    private void deleteInfo(String idText) throws IOException{
        Request request = new Request();
        Transactions transaction = new Transactions();
        transaction.setIdTransactions(idText);
        request.setRequestType(RequestType.DELETE_TRANSACTIONS);
        request.setRequestMessage((new Gson()).toJson(transaction));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

    }

    // ДОБАВИТЬ ПРОВЕРКИ НА ПОЛЯ РЕДАКТА
    // ПРИДЙМА ЧТО-НИБУДЬ С УДАЛЕНИЕМ
    private void updateInfo(String idText) throws IOException {
        Request request = new Request();
        //String type = "UPDATE_TRANSACTIONS";
        Transactions transaction = new Transactions();
        transaction.setIdTransactions(idText);
        //Companies compName = new Companies();
        //Products prodName = new Products();

        request.setRequestType(RequestType.UPDATE_TRANSACTIONS);
        request.setRequestMessage((new Gson()).toJson(transaction));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

//        ClientSocket.getInstance().getOut().println((new Gson()).toJson(compName));
//        ClientSocket.getInstance().getOut().flush();
//        ClientSocket.getInstance().getOut().println((new Gson()).toJson(prodName));
//        ClientSocket.getInstance().getOut().flush();
        String prod = ClientSocket.getInstance().getInStream().readLine();
        String comp = ClientSocket.getInstance().getInStream().readLine();
        String tr = ClientSocket.getInstance().getInStream().readLine();
        transaction = (new Gson()).fromJson(tr, Transactions.class);
        EndEdit.setVisible(true);
        EndEdit.setOnAction(actionEvent -> {
            String nameProduct = NameProductField.getText().trim();
            String nameCompany = this.nameCompany;
            String amount = AmountField.getText().trim();
            String date = DateField.getText().trim();
            String type = TypeField.getText().trim();
            IEClass ieClass;
            if (!nameProduct.equals("") && !nameCompany.equals("") && !amount.equals("") && !date.equals("") && !type.equals("")) {
                ieClass = new IEClass(idText,nameProduct, nameCompany, amount, date, type);
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
                ClientSocket.getInstance().getOut().flush();

                ClientSocket.getInstance().getOut().println((new Gson()).toJson(ieClass));
                ClientSocket.getInstance().getOut().flush();
            }
            else {
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("false"));
                ClientSocket.getInstance().getOut().flush();
                System.out.println("РЕДАКТ ВАЩЕ НЕ ВЫПОЛНЕН");

                errorText.setText("Поля не должны быть пустыми");
                Shake Error = new Shake(errorText);
                Fade errorTextFade = new Fade(errorText);
                errorTextFade.play();
                Error.playAnim();

            }
            EndEdit.setVisible(false);
        });
    }

    private void showInfo() throws IOException {
        ArrayList<IEClass> ieClasses = new ArrayList<>();
        ArrayList<Transactions> transactionsArray = new ArrayList<>();
        Request request = new Request();
        String type = "SHOW_TRANSACTIONS";
        request.setRequestType(RequestType.SHOW_TRANSACTIONS);
        request.setRequestMessage((new Gson()).toJson(type));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String ieclass = null;
        String tr = null;
        try {
            ieclass = ClientSocket.getInstance().getInStream().readLine();
            tr = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ieClasses = (new Gson()).fromJson(ieclass, new TypeToken<ArrayList<IEClass>>() {}.getType());
        ArrayList<IEClass> userTrans = new ArrayList<>();
//        for (int i = 0; i < ieClasses.size(); i++) {
//            if(ieClasses)
//        }
        transactionsArray = (new Gson()).fromJson(tr, new TypeToken<ArrayList<Transactions>>() {}.getType());
        for (IEClass im : ieClasses){
            if(im.getCompanyName().equals(this.nameCompany))
                userTrans.add(im);
        }
        //System.out.println(trans.toString());
        ObservableList<IEClass> user = FXCollections.observableArrayList(userTrans);
        Product.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        Company.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
        Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        idTransaction.setCellValueFactory(new PropertyValueFactory<>("idTransactions"));

        table.setItems(user);
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
    public void openNewSceneUser(String scene, User user){
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
        UserAppController userAppController = loader.getController();
        userAppController.setData(user);
        //
        stage.show();
        //
    }

}
