package org.example.productfinder;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {

    public Connection databaseLink;

    public Connection getDBConnection() {

        String databaseName = "products";
        String databaseUser = "root";
        String databasePassword = "root";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }
}
