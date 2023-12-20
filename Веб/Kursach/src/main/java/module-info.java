module java {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires com.google.gson;


    exports Client;
    exports Server;
    opens Server;
    opens Client;
    exports Client.TCP;
    opens Client.TCP to javafx.fxml, com.google.gson;
    exports Server.Enum;
    opens Server.Enum to javafx.fxml;
    exports Client.Enum;
    opens Client.Enum to javafx.fxml;
    exports Server.TCP;
    opens Server.TCP to com.google.gson;
    exports Client.Controller;
    opens Client.Controller;
    exports Client.DBClass;
    opens Client.DBClass;
    opens Server.DBClass;
    exports Server.DBClass;
    //exports Server.TCP;

}