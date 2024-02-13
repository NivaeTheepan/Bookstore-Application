/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author lalith
 */
public class CustomerList {
    
    static Scene customerListMenu;
    static ObservableList<Customer> customers = FXCollections.observableArrayList();;
    
    public static void display(Stage application, Scene back) {
        TableView table = new TableView();
        VBox layout = new VBox(table);
        
        Button bck = new Button("Back");
        bck.setOnAction(e -> application.setScene(back));
        
        Button del = new Button("Delete");
        del.setOnAction(e -> {
            Customer customerDelete = (Customer)table.getSelectionModel().getSelectedItem();
            deleteCustomer(customers, customerDelete);
            table.refresh();
        });
        
        Label username_label = new Label("Username: ");
        TextField usernameField = new TextField();
        Label password_label = new Label("Password: ");
        PasswordField passwordField = new PasswordField();
        
        HBox usernameHBox = new HBox(username_label, usernameField);
        HBox passwordHBox = new HBox(password_label, passwordField);
        
        Button add = new Button("Add");
        add.setOnAction(e -> {
            if (!(usernameField.getText().equals("") || passwordField.getText().equals(""))) {
                Customer customerAdd = new Customer(usernameField.getText(), passwordField.getText(), 0);
                addCustomer(customers, customerAdd);
                usernameField.clear(); passwordField.clear();
            }
            table.refresh();
        });
        
        TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        TableColumn<Customer, String> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        
        table.setItems(customers); // get data from the customers list
        table.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);
        
        layout.getChildren().addAll(bck, del, usernameHBox, passwordHBox, add);
        
        customerListMenu = new Scene(layout, back.getWidth(), back.getHeight());
        application.setScene(customerListMenu);
    }
    
    public static void retrieveCustomersFromFile(String file, ObservableList<Customer> customerList) {
        try {
            Scanner in = new Scanner(new File(file));
            String[] line;
            customerList.clear();
            while (in.hasNextLine()) {
                line = in.nextLine().split(", ");
                customerList.add(new Customer(line[0], line[1], Integer.parseUnsignedInt(line[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    
    public static void identifyStatus(Customer customer) {
        if (customer.getPoints() >= 1000) {
            customer.setStatus("Gold");
        } else {
            customer.setStatus("Silver");
        }
    }
    
    public static void deleteCustomer(ObservableList<Customer> customerList, Customer customer) {
        customerList.remove(customer);
    }
    
    public static void addCustomer(ObservableList<Customer> customerList, Customer customer) {
        boolean existingCustomer = false;
        
        for (Customer customerInList: customerList) {
            if(customerInList.getUsername().equals(customer.getUsername())) {
                existingCustomer = true;
                break;
            }
        }
        
        boolean trialAdmin = customer.getUsername().toLowerCase().equals("admin");
        
        if(existingCustomer) {
            Alert existingUser = new Alert(AlertType.ERROR);
            existingUser.setHeaderText("This user already exists.");
            existingUser.setContentText(null);
            existingUser.showAndWait();
        } else if (trialAdmin) {
            Alert adminAttempt = new Alert(AlertType.ERROR);
            adminAttempt.setHeaderText("Cannot create new admin.");
            adminAttempt.setContentText(null);
            adminAttempt.showAndWait();
        } else {
            customerList.add(customer);
        }
    }
}
