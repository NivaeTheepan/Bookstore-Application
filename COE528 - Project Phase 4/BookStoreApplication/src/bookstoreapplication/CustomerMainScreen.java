/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author lalith
 */
public class CustomerMainScreen {
    
    static Scene customerMenu;
    
    public static void display(Stage application, Scene mainScene, int index) {
        Customer loginCustomer = CustomerList.customers.get(index);
        
        TableView table = new TableView();
        VBox layout = new VBox();
        VBox list = new VBox(table);
        
        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            if (loginCustomer.getCart().size() > 0) {
                for (Book book : loginCustomer.getCart()) {
                    BooksList.addBook(book);
                }
                loginCustomer.clearCart();
            }
            application.setScene(mainScene);
                });
        
        Button addCart = new Button("Add to cart");
        addCart.setOnAction(e -> {
            Book bookToBuy = (Book)table.getSelectionModel().getSelectedItem();
            loginCustomer.addToCart(bookToBuy);
            BooksList.deleteBook(BooksList.books, bookToBuy);            
        });
        Button goCart = new Button("Go to cart");
        goCart.setOnAction(e -> {
            if(loginCustomer.getCart().size() > 0) {
                CustomerCart.display(application, loginCustomer, customerMenu);
            } else {
                Alert emptyCart = new Alert(AlertType.WARNING);
                emptyCart.setHeaderText("Your cart is empty. Add books to cart before proceeding");
                emptyCart.showAndWait();
            }
        });
        
        HBox cartOptions = new HBox();
        cartOptions.getChildren().addAll(addCart, goCart);
        
        Label intro = new Label();
        intro.setText("Welcome " + loginCustomer.getUsername() + ". You have " + Integer.toString(loginCustomer.getPoints()) + " points. Your status is " + loginCustomer.getStatus());
        
        TableColumn<Customer, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Customer, String> priceColumn = new TableColumn<>("Price($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        table.setItems(BooksList.books);
        table.getColumns().addAll(titleColumn, priceColumn);
        
        layout.getChildren().addAll(intro, list, cartOptions, logout);
        
        customerMenu = new Scene(layout, mainScene.getWidth(), mainScene.getHeight());
        
        application.setScene(customerMenu);
    }
}
