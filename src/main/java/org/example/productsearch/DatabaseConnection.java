package org.example.productsearch;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getDBConnection() {

        String databaseName = "myDB";
        String databaseUser = "wind";
        String databasePassword = "Soulofwind@1";
        String url = "jdbc:mysql://192.168.2.4/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }
}
