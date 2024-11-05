module org.example.productfinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens org.example.productfinder to javafx.fxml;
    exports org.example.productfinder;
}