module org.example.productsearch {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens org.example.productsearch to javafx.fxml;
    exports org.example.productsearch;
}