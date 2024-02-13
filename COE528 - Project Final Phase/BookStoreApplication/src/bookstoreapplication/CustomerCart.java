/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author lalith
 */
public class CustomerCart {
    
    static Scene cartMenu;
    static double totalCost;
    
    public static void display(Stage application, Customer customer, Scene back) {
        TableView table = new TableView();
        VBox layout = new VBox(table);
        
        totalCost = totalCostCart(customer.getCart());
        
        HBox buyingOptions = new HBox();
        Button buyCash = new Button("Buy with cash");
        Button buyPoints = new Button("Redeem Points");
        
        buyCash.setOnAction(e -> {
            totalCost = totalCostCart(customer.getCart());
            
            for (Book book : customer.getCart()) {
                customer.buyBook(book);
                BooksList.deleteBook(BooksList.books, book);
            }
            
            customer.clearCart();
            
            CustomerList.identifyStatus(customer);
            
            double[] dimensions = {cartMenu.getWidth(), cartMenu.getHeight()};
            BuyScreen.display(application, totalCost, customer, dimensions);
        });
        buyPoints.setOnAction(e -> {
            totalCost = totalCostCart(customer.getCart());
            
            for (Book book : customer.getCart()) {
                try {
                    customer.buyWithPoints(book);
                    BooksList.deleteBook(BooksList.books, book);
                } catch (IllegalArgumentException exc) {
                    BooksList.addBook(book);
                    Alert insufficientPoints = new Alert(AlertType.ERROR);
                    insufficientPoints.setHeaderText("Insufficient points.");
                    insufficientPoints.setContentText(null);
                    insufficientPoints.showAndWait();
                    if (insufficientPoints.getResult() == ButtonType.OK) {
                        break;
                    }
                }
            }
            
            customer.clearCart();
            
            CustomerList.identifyStatus(customer);
            
            double[] dimensions = {cartMenu.getWidth(), cartMenu.getHeight()};
            BuyScreen.display(application, totalCost, customer, dimensions);
        });
        buyingOptions.getChildren().addAll(buyCash, buyPoints);  
        
        Label total = new Label("Total Cost: $" + Double.toString(Math.round(totalCost * (Math.pow(10, 2))) / (Math.pow(10, 2))));
        
        Button deleteCart = new Button("Delete");
        deleteCart.setOnAction(e -> {
            Book removeBook = (Book)table.getSelectionModel().getSelectedItem(); // store the selected option as a book
            //System.out.println(removeBook);
            BooksList.addBook(removeBook);
            customer.removeFromCart(removeBook);
            
            if (table.getItems().size() < 1) {
                application.setScene(back);
            }
        });
        
        Button bck = new Button("Back");
        bck.setOnAction(e -> application.setScene(back));
        
        TableColumn<Customer, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Customer, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        table.setItems(customer.getCart());
        table.getColumns().addAll(titleColumn, priceColumn);
        
        layout.getChildren().addAll(total, deleteCart, buyingOptions, bck);
        
        cartMenu = new Scene(layout, back.getWidth(), back.getHeight());
        
        application.setScene(cartMenu);
    }
    
    private static double totalCostCart(ObservableList<Book> books) {
        double cost = 0;
        for (Book book : books) {
            cost += book.getPrice();
        }
        return cost;
    }
}
