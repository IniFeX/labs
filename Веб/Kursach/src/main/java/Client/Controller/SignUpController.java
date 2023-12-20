package Client.Controller;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

//import Server.DBClass.Companies;
import Client.ClientSocket;
import Client.DBClass.Companies;
import Client.DBClass.User;
import Client.Enum.RequestType;
import Client.TCP.Request;
import animation.Fade;
import animation.Shake;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> CompanyChoiceBox;

    @FXML
    private Label errorText;

    @FXML
    private ToggleGroup Gender;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signUpButton;

    @FXML
    private RadioButton signUpRadioButtonFemale;

    @FXML
    private RadioButton signUpRadioButtonMale;

    @FXML
    void initialize() throws IOException {
        signUpNewUser();
    }


    private void signUpNewUser() throws IOException{


        ArrayList<String> companyName = new ArrayList<>();
        Gson gson = new Gson();
        Request request = new Request();
        System.out.println("START");
//        String answer = ClientSocket.getInstance().getInStream().readLine();
//        Request responseModel = gson.fromJson(answer, Request.class);
//        companyName = gson.fromJson(responseModel.getRequestMessage(), new TypeToken<ArrayList<String>>(){}.getType());
//        System.out.println(companyName);
////        System.out.println(responseModel.getRequestMessage());

        //companyName = new Gson().fromJson(responseModel.getRequestMessage(), ArrayList.class);
        //System.out.println(responseModel.getResponseMessage());
        String type = "SIGN_UP_USER";
        request.setRequestType(RequestType.SIGN_UP_USER);
        request.setRequestMessage((new Gson()).toJson(type));
        ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
        ClientSocket.getInstance().getOut().flush();
        System.out.println("Send request");

//        ClientSocket.getInstance().getOut().println(type);
//        ClientSocket.getInstance().getOut().flush();
        //DatabaseHandler dbHandler = new DatabaseHandler();

        String as = ClientSocket.getInstance().getInStream().readLine();
        companyName = gson.fromJson(as, new TypeToken<ArrayList<String>>(){}.getType());
        System.out.println(companyName);
        CompanyChoiceBox.getItems().addAll(companyName);
        System.out.println("Get company");
        signUpButton.setOnAction(actionEvent -> {
            String loginText = login_field.getText().trim();
            String loginPass = password_field.getText().trim();
            if (!loginText.equals("") && !loginPass.equals("")) {
                String login = login_field.getText();
                String password = password_field.getText();
                String gender;
                String role = "Пользователь";
                String idCompany =  "3";
                if(signUpRadioButtonMale.isSelected())
                    gender = "М";
                else
                    gender = "Ж";
                User userCheck = new User();
                userCheck.setLogin(login);
                userCheck.setPassword(password);

                String compName = CompanyChoiceBox.getValue();
                Companies companies = new Companies();
                companies.setName(compName);


                ClientSocket.getInstance().getOut().println((new Gson()).toJson(companies));
                ClientSocket.getInstance().getOut().flush();
                System.out.println("Send company");
                String recId;
                try {
                    recId = ClientSocket.getInstance().getInStream().readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                idCompany = gson.fromJson(recId, String.class);
                System.out.println(idCompany);

                ClientSocket.getInstance().getOut().println((new Gson()).toJson(userCheck));
                ClientSocket.getInstance().getOut().flush();
                System.out.println("Send user");
                String count;
                try {
                    count = ClientSocket.getInstance().getInStream().readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int counter = (new Gson()).fromJson(count, Integer.class);
                System.out.println("counter: " + counter);
                String ch;
                if(counter >= 1){
                    System.out.println("FALSE");
                    errorText.setText("Логин уже занят");
                    Fade errorTextFade = new Fade(errorText);
                    errorTextFade.play();
                    ch = "false";
                    ClientSocket.getInstance().getOut().println((new Gson()).toJson(ch));
                    ClientSocket.getInstance().getOut().flush();
                    openNewScene("/signUp.fxml");
                } else{
                    System.out.println("TRUE");
                    ch = "true";
                    ClientSocket.getInstance().getOut().println((new Gson()).toJson(ch));
                    ClientSocket.getInstance().getOut().flush();
                    System.out.println("Succes");
                    User user = new User(login, password, gender, role, idCompany);
                    //dbHandler.AddUser(user);
                    ClientSocket.getInstance().getOut().println((new Gson()).toJson(user));
                    ClientSocket.getInstance().getOut().flush();
                    openNewScene("/home.fxml");
                }
                System.out.println("END");

            } else {
                errorText.setText("Поля не должны быть пустыми");
                Fade errorTextFade = new Fade(errorText);
                errorTextFade.play();
                Shake userLoginAnim = new Shake(login_field);
                Shake userPassAnim = new Shake(password_field);
                userLoginAnim.playAnim();
                userPassAnim.playAnim();
            }

        });

    }

    private void openNewScene(String scene) {
        signUpButton.getScene().getWindow().hide();

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
        stage.show();
    }
}
