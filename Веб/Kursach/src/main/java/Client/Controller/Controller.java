package Client.Controller;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

import Client.ClientSocket;
import Client.Enum.RequestType;
import Client.TCP.Request;
import Client.DBClass.User;
//import Server.DBClass.User;
//import Server.Enum.RequestType;
//import Server.TCP.Request;
import animation.Fade;
import animation.Shake;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Controller extends BaseScreen {
    public Controller(){

    }
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label errorText;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field;
    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {
        authSignInButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String loginPass = password_field.getText().trim();
//            loginText = "админ";
//            loginPass = "админ";
            if (!loginText.equals("") && !loginPass.equals("")) {
                try {
                    loginUser(loginText, loginPass);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                errorText.setText("Поля не должны быть пустыми");
                Shake userLoginAnim = new Shake(login_field);
                Shake userPassAnim = new Shake(password_field);
                userLoginAnim.playAnim();
                userPassAnim.playAnim();
            }
        });

        loginSignUpButton.setOnAction(event -> {
            openNewScene("/signUp.fxml");
        });


    }

    private void loginUser (String loginText, String loginPass) throws IOException {
        Request request = new Request();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPass);
        String type = "LOGIN_USER";

        request.setRequestType(RequestType.LOGIN_USER);
        request.setRequestMessage((new Gson()).toJson(user));

        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();
        System.out.println("Second send");
        int counter = 0;
        String chAd = ClientSocket.getInstance().getInStream().readLine();
        String checkAdmin = (new Gson()).fromJson(chAd, String.class);

        String cont = ClientSocket.getInstance().getInStream().readLine();
        counter = (new Gson()).fromJson(cont, Integer.class);
        System.out.println(counter);
        System.out.println("COUNTER = " + counter);
        if(counter >= 1 && checkAdmin.equals("false")){
            System.out.println("Succes");
            openNewSceneUser("/userApp.fxml", user);
        } else if (counter >= 1 && checkAdmin.equals("true")) {
            System.out.println("Admin");
            openNewScene("/app.fxml");
        } else {
            errorText.setText("Логин или пароль введены неверно");
            Fade errorTextFade = new Fade(errorText);
            errorTextFade.play();
            Shake userLoginAnim = new Shake(login_field);
            Shake userPassAnim = new Shake(password_field);
            userLoginAnim.playAnim();
            userPassAnim.playAnim();
        }

    }

    public void openNewScene(String scene){
        loginSignUpButton.getScene().getWindow().hide();
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
        loginSignUpButton.getScene().getWindow().hide();
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
