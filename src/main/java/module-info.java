module org.example.oop_ca5_shane_favour {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;
    requires java.sql;

    // Open packages to JavaFX that need reflection access
    opens MoviesDaoGui.DTOs to javafx.base;  // Critical for PropertyValueFactory
    opens MoviesDaoGui.GUI to javafx.fxml;
    opens MoviesDaoGui.MovieObjects to javafx.graphics;

    exports MoviesDaoGui.MovieObjects;
    exports MoviesDaoGui.GUI;
    exports MoviesDaoGui.DTOs;  // Make sure to export DTOs if used elsewhere
}