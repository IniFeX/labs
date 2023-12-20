package Client.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientSocket;
import Client.DBClass.Products;
import Client.DBClass.User;
import Client.Enum.RequestType;
import Client.TCP.Request;
import Client.DBClass.Transactions;
import animation.Fade;
import animation.Shake;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserAppController {
    private User user;
    private ArrayList<Transactions> transactionsArray;
    private String nameCompany;

    public void setData(User user){
        this.user = user;
        System.out.println("user controller: " + user.getLogin());
        getInfo(user);
        this.nameCompany = getName();
        this.transactionsArray = getTrans();
        System.out.println(transactionsArray);
        System.out.println("company: " + this.nameCompany);
        System.out.println(this.user.getIdUser());
        System.out.println(this.user.getLogin());
        System.out.println(this.user.getGender());
        System.out.println(this.user.getRole());
        System.out.println(this.user.getIdCompany());
        System.out.println(this.user.getPassword());

    }
    public ArrayList<Transactions> getTrans(){
        ArrayList<Transactions> transactions = new ArrayList<>();
        String company = this.user.getIdCompany();
        Request request = new Request();
        request.setRequestType(RequestType.TRANSACTION_BY_IDCOMPANY);
        request.setRequestMessage((new Gson()).toJson(company));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();
        String js = " ";
        try {
            js = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        transactions = (new Gson()).fromJson(js, new TypeToken<ArrayList<Transactions>>() {}.getType());
        return transactions;

    }
    public void getInfo(User user){
        Request request = new Request();
        request.setRequestType(RequestType.GET_USER_INFO);
        request.setRequestMessage((new Gson()).toJson(user));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();
        String jsGet = " ";
        try {
            jsGet = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        User userGet = (new Gson()).fromJson(jsGet, User.class);
        this.user = userGet;
    }
    public String getName(){
        String nameCompany = " ";
        String company = this.user.getIdCompany();
        Request request = new Request();
        request.setRequestType(RequestType.COMPANY_BY_ID);
        request.setRequestMessage((new Gson()).toJson(company));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();
        String js = " ";
        try {
            js = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nameCompany = (new Gson()).fromJson(js, String.class);
        return nameCompany;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ProfitButton2;
    @FXML
    private BarChart<String, Number> ProfitDiagram2;
    @FXML
    private NumberAxis y2;
    @FXML
    private CategoryAxis x2;
    @FXML
    private Button Import;

    @FXML
    private Button NalogButton;

    @FXML
    private Button ProfitButton;

    @FXML
    private Button backButton;

    @FXML
    private BarChart<String, Number> ProfitDiagram;
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    @FXML
    private BarChart<String, Number> ProfitDiagram1;
    @FXML
    private CategoryAxis x1;
    @FXML
    private NumberAxis y1;
    @FXML
    private TextField exportNalog;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Button ChangeData;
    @FXML
    private TextField importNalog;
    @FXML
    void initialize() {
        //String name = getName();
//        System.out.println(this.nameCompany);
//        companyText.setText(this.nameCompany);
        ChangeData.setOnAction(actionEvent -> {
                    String login = loginField.getText().trim();
                    String password = passwordField.getText().trim();
                    if (!login.equals("") && !password.equals("")) {
                        try {
                            editUser(login, password);
                            System.out.println("УДАЛ ВЫПОЛНЕН");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("УДАЛ НЕ ВЫПОЛНЕН");
//                        errorText.setText("Поле не должно быть пустым");
//                        Shake Error = new Shake(errorText);
//                        Fade errorTextFade = new Fade(errorText);
//                        errorTextFade.play();
//                        Error.playAnim();
                    }
                });
            backButton.setOnAction(actionEvent -> {
            openNewScene("/home.fxml");
        });
        NalogButton.setOnAction(actionEvent -> {
            nalogi();
        });
        Import.setOnAction(actionEvent -> {
            openNewSceneUser("/TransactionUser.fxml", this.user);
        });
        ProfitButton.setOnAction(actionEvent -> {
            profitDiagram();
        });
        ProfitButton2.setOnAction(actionEvent -> {
            profitDiagram2();
        });
    }

    private void profitDiagram2() {
        ArrayList<Products> products = new ArrayList<>();
        Request request = new Request();
        request.setRequestType(RequestType.PRODUCT_TRANSACTION);
        request.setRequestMessage((new Gson()).toJson(transactionsArray));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String js = " ";
        try {
            js = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        products = (new Gson()).fromJson(js, new TypeToken<ArrayList<Products>>() {}.getType());
        int Import = 0;
        int Export = 0;
        int Profit = 0;
        for(Transactions tr : transactionsArray) {
            System.out.println("Amount: " + tr.getAmount());
        }
        for (Products pr : products) {
            System.out.println("Price: " + pr.getPrice());
        }
        XYChart.Series<String, Number> seriesIm = new XYChart.Series();
        XYChart.Series<String, Number> seriesEx = new XYChart.Series();
        seriesIm.setName("Импорт");
        seriesEx.setName("Экспорт");

        for(Transactions tr : transactionsArray){
            for(Products pr : products){
                if(tr.getIdProduct().equals(pr.getIdProduct())){
                    if(tr.getType().equals("импорт")) {
                        Import = Integer.parseInt(tr.getAmount()) * Integer.parseInt(pr.getPrice());
                        seriesIm.getData().add(new XYChart.Data<>(tr.getDate(), Import));
                    }
                    else {
                        Export = Integer.parseInt(tr.getAmount()) * Integer.parseInt(pr.getPrice());
                        seriesEx.getData().add(new XYChart.Data<>(tr.getDate(), Export));
                    }
                }

            }
        }
        Profit = Export - Import;
        System.out.println("Import: " + Import + " Export: " + Export + " Profit: " + Profit);

//        series.getData().add(new XYChart.Data<>("Импорт", Import));
//        series.getData().add(new XYChart.Data<>("Экспорт", Export));
//        series.getData().add(new XYChart.Data<>("Доход", Profit));
        ProfitDiagram2.setBarGap(0);
        ProfitDiagram2.setCategoryGap(10);
        ProfitDiagram2.getData().clear();

        ProfitDiagram2.getData().addAll(seriesIm, seriesEx);
//        ProfitDiagram.setBarGap(0);
//        ProfitDiagram.setCategoryGap(10);


    }

    private void editUser(String loginn, String passwordn) throws IOException {
        Request request = new Request();
        User user = new User();
        user.setLogin(loginn);

        request.setRequestType(RequestType.EDIT_USER_BY_LOGIN);
        request.setRequestMessage((new Gson()).toJson(this.user));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

            String login = loginn;
            String password = passwordn;

            User us;
            if(!login.equals("") && !password.equals("")){
                us = new User();
                us.setLogin(loginn);
                us.setPassword(passwordn);
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("true"));
                ClientSocket.getInstance().getOut().flush();

                ClientSocket.getInstance().getOut().println((new Gson()).toJson(us));
                ClientSocket.getInstance().getOut().flush();
                openNewScene("/home.fxml");
            }
            else{
                ClientSocket.getInstance().getOut().println((new Gson()).toJson("false"));
                ClientSocket.getInstance().getOut().flush();
                System.out.println("РЕДАКТ ВАЩЕ НЕ ВЫПОЛНЕН");

//                errorText1.setText("Поля не должны быть пустыми");
//                Shake Error = new Shake(errorText1);
//                Fade errorTextFade = new Fade(errorText1);
//                errorTextFade.play();
//                Error.playAnim();

            }


    }

    private void nalogi() {
        double nalogImport = 0.18;
        double nalogExport = 0.18;

        String exp = exportNalog.getText().trim();
        String imp = importNalog.getText().trim();
        if(!exp.equals("") && !imp.equals("")){
            nalogImport = Double.parseDouble(imp)/100;
            nalogExport = Double.parseDouble(exp)/100;
        }
        ArrayList<Products> products = new ArrayList<>();
        Request request = new Request();
        request.setRequestType(RequestType.PRODUCT_TRANSACTION);
        request.setRequestMessage((new Gson()).toJson(transactionsArray));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String js = " ";
        try {
            js = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        products = (new Gson()).fromJson(js, new TypeToken<ArrayList<Products>>() {}.getType());
        int Import = 0;
        int Export = 0;
        int Profit = 0;
        for(Transactions tr : transactionsArray) {
            System.out.println("Amount: " + tr.getAmount());
        }
        for (Products pr : products) {
            System.out.println("Price: " + pr.getPrice());
        }
        for(Transactions tr : transactionsArray){
            for(Products pr : products){
                if(tr.getIdProduct().equals(pr.getIdProduct())){
                    if(tr.getType().equals("импорт"))
                        Import += Integer.parseInt(tr.getAmount()) * Integer.parseInt(pr.getPrice());
                    else
                        Export += Integer.parseInt(tr.getAmount()) * Integer.parseInt(pr.getPrice());
                }

            }
        }
        nalogExport *= Import;
        nalogImport *= Export;
        XYChart.Series<String, Number> series1 = new XYChart.Series();
        series1.setName("Тип операции");

        series1.getData().add(new XYChart.Data<>("Импорт", nalogExport));
        series1.getData().add(new XYChart.Data<>("Экспорт", nalogImport));
        ProfitDiagram1.setBarGap(0);
        ProfitDiagram1.setCategoryGap(10);
        ProfitDiagram1.getData().clear();

        ProfitDiagram1.getData().add(series1);

    }

    private void profitDiagram() {
        //ArrayList<Transactions> transactions = new ArrayList<>();

        ArrayList<Products> products = new ArrayList<>();
        Request request = new Request();
        request.setRequestType(RequestType.PRODUCT_TRANSACTION);
        request.setRequestMessage((new Gson()).toJson(transactionsArray));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String js = " ";
        try {
            js = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        products = (new Gson()).fromJson(js, new TypeToken<ArrayList<Products>>() {}.getType());
        int Import = 0;
        int Export = 0;
        int Profit = 0;
        for(Transactions tr : transactionsArray) {
            System.out.println("Amount: " + tr.getAmount());
        }
        for (Products pr : products) {
            System.out.println("Price: " + pr.getPrice());
        }
            for(Transactions tr : transactionsArray){
                for(Products pr : products){
                    if(tr.getIdProduct().equals(pr.getIdProduct())){
                        if(tr.getType().equals("импорт"))
                            Import += Integer.parseInt(tr.getAmount()) * Integer.parseInt(pr.getPrice());
                        else
                            Export += Integer.parseInt(tr.getAmount()) * Integer.parseInt(pr.getPrice());
                }

            }
        }
        Profit = Export - Import;
        System.out.println("Import: " + Import + " Export: " + Export + " Profit: " + Profit);
        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("Тип операции");

        series.getData().add(new XYChart.Data<>("Импорт", Import));
        series.getData().add(new XYChart.Data<>("Экспорт", Export));
        series.getData().add(new XYChart.Data<>("Доход", Profit));
        ProfitDiagram.setBarGap(0);
        ProfitDiagram.setCategoryGap(10);
        ProfitDiagram.getData().clear();

        ProfitDiagram.getData().add(series);
//        ProfitDiagram.setBarGap(0);
//        ProfitDiagram.setCategoryGap(10);

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
        TransactionUserController transactionUserController = loader.getController();
        transactionUserController.setData(user, this.nameCompany);
        //
        stage.show();
        //
    }

}
