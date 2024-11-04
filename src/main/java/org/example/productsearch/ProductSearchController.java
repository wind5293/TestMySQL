package org.example.productsearch;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductSearchController implements Initializable {
    @FXML
    public TextField keywordTextField;
    @FXML
    private TableView<ProductSearchModel> productTableView;
    @FXML
    private TableColumn<ProductSearchModel, Integer> productIDTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, String> brandTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, String> modelNumberTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, Integer> modelYearTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, String> productNameTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, String> descriptionTableColumn;

    ObservableList<ProductSearchModel> productSearchModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resource) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDBConnection();

        String productViewQuery = "select ProductID, Brand, ModelNumber, ModelYear, ProductName, Description from Products";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(productViewQuery);

            while (queryOutput.next()) {

                Integer queryProductID = queryOutput.getInt("ProductID");
                String queryBrand = queryOutput.getString("Brand");
                String queryModelNumber = queryOutput.getString("ModelNumber");
                Integer queryModelYear = queryOutput.getInt("ModelYear");
                String queryProductName = queryOutput.getString("ProductName");
                String queryDescription = queryOutput.getString("Description");

                //Populate the ObservableList
                productSearchModelObservableList.add(new ProductSearchModel(queryProductID, queryBrand, queryModelNumber, queryModelYear, queryProductName, queryDescription));
            }

            //PropertyValueFactory corresponds to new ProductSearchModel fields
            // The table columns is the one you annotate above.
            productIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
            brandTableColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
            modelNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("modelNumber"));
            modelYearTableColumn.setCellValueFactory(new PropertyValueFactory<>("modelYear"));
            productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

            productTableView.setItems(productSearchModelObservableList);

            FilteredList<ProductSearchModel> filteredData = new FilteredList<>(productSearchModelObservableList, b -> true);

            keywordTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
                filteredData.setPredicate(productSearchModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (productSearchModel.getProductName().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getDescription().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getBrand().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getModelNumber().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getModelYear().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;

                });
            }));

            SortedList<ProductSearchModel> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(productTableView.comparatorProperty());

            productTableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(ProductSearchController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }


}