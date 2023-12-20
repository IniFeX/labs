package Client.Controller;

import Client.ClientSocket;
import Client.DBClass.Products;
import Client.Enum.RequestType;
import Client.TCP.Request;
import Client.IEClass;
import Client.DBClass.Companies;
import Client.DBClass.Transactions;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ImportExportController {
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
    private Button pieButton;
    @FXML
    private Button ProfitButton;
    @FXML
    private Button ImportButton;
    @FXML
    private Button ExportButton;

    @FXML
    private PieChart pieChart;
    @FXML
    private PieChart pieChart2;
    @FXML
    private PieChart pieChart1;


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
        pieButton.setOnAction(actionEvent -> {
            //showDiagram();
        });
        ProfitButton.setOnAction(actionEvent -> {
            showDiagram("profit");
        });
        ImportButton.setOnAction(actionEvent -> {
            showDiagram("import");
        });
        ExportButton.setOnAction(actionEvent -> {
            showDiagram("export");
        });

        backButton.setOnAction(actionEvent -> {
            openNewScene("/app.fxml");
        });

    }

    private void showDiagram(String type) {
//        ProfitButton.setVisible(true);
//        ImportButton.setVisible(true);
//        ExportButton.setVisible(true);
        Request request = new Request();
        request.setRequestType(RequestType.DIAGRAM);
        request.setRequestMessage("asd");
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String jsTrans = " ";
        String jsProd = " ";
        String jsComp = " ";
        try {
            jsTrans = ClientSocket.getInstance().getInStream().readLine();
            jsProd = ClientSocket.getInstance().getInStream().readLine();
            jsComp = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Transactions> transList = (new Gson()).fromJson(jsTrans, new TypeToken<ArrayList<Transactions>>() {}.getType());
        ArrayList<Products> prodList = (new Gson()).fromJson(jsProd, new TypeToken<ArrayList<Products>>() {}.getType());
        ArrayList<Companies> compList = (new Gson()).fromJson(jsComp, new TypeToken<ArrayList<Companies>>() {}.getType());

        ArrayList<Integer> impList = new ArrayList<>();
        ArrayList<Integer> expList = new ArrayList<>();
        ArrayList<Integer> prfList = new ArrayList<>();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> pieChartData3 = FXCollections.observableArrayList();

        for (Companies cp : compList){
            int Import = 0;
            int Export = 0;
            int Profit = 0;

            for(Transactions tr : transList){
                for(Products pr : prodList){
                    if(tr.getIdProduct().equals(pr.getIdProduct()) && tr.getIdCompany().equals(cp.getIdCompany())){
                        if(tr.getType().equals("импорт")) {
                            Import += Integer.parseInt(tr.getAmount()) * Integer.parseInt(pr.getPrice());
                        }
                        else {
                            Export += Integer.parseInt(tr.getAmount()) * Integer.parseInt(pr.getPrice());
                        }
                    }

                }
            }
            Profit = Export - Import;
            impList.add(Import);
            expList.add(Export);
            prfList.add(Profit);
            Profit = Export - Import;
            pieChartData.add(new PieChart.Data(cp.getName(),Import));
            pieChartData2.add(new PieChart.Data(cp.getName(),Export));
            pieChartData3.add(new PieChart.Data(cp.getName(),Profit));
        }
        System.out.println(impList);
        System.out.println(expList);
        System.out.println(prfList);
       // pieChart.getData().clear();
//        pieChart1.getData().clear();
//        pieChart2.getData().clear();
        pieChart.getData().clear();
        if(type.equals("import"))
            pieChart.getData().addAll(pieChartData);
        if(type.equals("export"))
            pieChart.getData().addAll(pieChartData2);
        if(type.equals("profit"))
            pieChart.getData().addAll(pieChartData3);



    }

    private void addInfo() {
        Request request = new Request();
        String nameProduct = NameProductField.getText().trim();
        String nameCompany = NameCompanyField.getText().trim();
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
            String nameCompany = NameCompanyField.getText().trim();
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
        transactionsArray = (new Gson()).fromJson(tr, new TypeToken<ArrayList<Transactions>>() {}.getType());
        for (IEClass im : ieClasses)
            System.out.println(im);
        //System.out.println(trans.toString());
        ObservableList<IEClass> transList = FXCollections.observableArrayList(ieClasses);
        Product.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        Company.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
        Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        idTransaction.setCellValueFactory(new PropertyValueFactory<>("idTransactions"));

        table.setItems(transList);
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
