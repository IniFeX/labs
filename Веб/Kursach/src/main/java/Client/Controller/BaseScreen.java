package Client.Controller;

import Client.ClientSocket;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class BaseScreen extends Stage {

    public BaseScreen(){
        setOnCloseRequest(event -> {
            event.consume(); // Предотвращаем автоматическое закрытие окна
            System.out.println("try close");
        });
    }
}