module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;
    requires javafx.swing;
    requires freetts;


    opens com.example.demo to javafx.fxml;
    opens com.example.demo.controllers to javafx.fxml;
    exports com.example.demo;
}