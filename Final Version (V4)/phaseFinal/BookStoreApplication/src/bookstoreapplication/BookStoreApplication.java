/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author lalith
 */
public class BookStoreApplication extends Application {
    
    static Scene loginScreen;
    
    @Override
    public void start(Stage primaryStage) {
        CustomerList.retrieveCustomersFromFile("customers.txt", CustomerList.customers);
        BooksList.retrieveBooksFromFile("books.txt", BooksList.books);
        
        GridPane layout = new GridPane(); // create a grid layout
        layout.setHgap(10); // set the spacing between elements horizontally
        layout.setVgap(12);// set the spacing between elements vertically
        
        loginScreen = new Scene(layout, 640, 400);
        
        Label title = new Label("Welcome to the BookStore App");
        title.setTranslateX(150); title.setTranslateY(150); // move/translate element in the screen
        title.setFont(new Font(15));
        Label username_label = new Label("Username:");
        username_label.setTranslateX(150); username_label.setTranslateY(190);
        username_label.setFont(new Font(13));
        Label password_label = new Label("Password:");
        password_label.setTranslateX(150); password_label.setTranslateY(220);
        password_label.setFont(new Font(13));
        
        TextField username_field = new TextField();
        username_field.setTranslateX(250); username_field.setTranslateY(190);
        username_field.setMaxWidth(130);
        PasswordField password_field = new PasswordField();       
        password_field.setTranslateX(250); password_field.setTranslateY(220);
        password_field.setMaxWidth(130);
        
        Button login = new Button("Login");
        login.setOnAction(e -> { // when user clicks login button, following code happens
            String[] enteredInfo = {username_field.getText(), password_field.getText()};
            username_field.clear(); password_field.clear();
            int customerNum = 0; // to know who's logging in
            boolean validUser = false, validAdmin = false;
            if (enteredInfo[0].equals("admin") && enteredInfo[1].equals("admin")) {
                validAdmin = true;
                OwnerScreen.mainScreen(primaryStage, loginScreen); //open the admin portal
            } else {
                for (Customer customer : CustomerList.customers) { // for each loop
                    if(enteredInfo[0].equals(customer.getUsername()) && enteredInfo[1].equals(customer.getPassword())) {
                        validUser = true;
                        break;
                    }
                    customerNum++;
                }
            }
            
            if (validUser) {
                CustomerMainScreen.display(primaryStage, loginScreen, customerNum);
            } else if (!validUser && !validAdmin) {
                Alert incorrectLogin = new Alert(AlertType.ERROR);
                incorrectLogin.setTitle("Error 404");
                incorrectLogin.setHeaderText("Incorrect username or password. Please try again.");
                incorrectLogin.setContentText(null);
                incorrectLogin.showAndWait();
            }
        });
            
        login.setTranslateX(250); login.setTranslateY(250);
        
        layout.getChildren().addAll(title, username_label, password_label, username_field, password_field, login);
        
        // these 3 lines are the main commands that run before the rest
        primaryStage.setTitle("BookStore App");
        primaryStage.setScene(loginScreen);
        primaryStage.show();
        
        // this runs when user clicks 'X' button
        primaryStage.setOnCloseRequest(e ->  {
            Alert confirmation = new Alert(AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm");
            confirmation.setHeaderText("Are you sure you want to close this program?");
            confirmation.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
            confirmation.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait();
            
            ButtonType result = confirmation.getResult();
            
            if (result == ButtonType.YES) {
                
                // writing customers and books list to file
                try {
                    FileWriter f = new FileWriter(new File("customers.txt"), false);
                    for (Customer customer : CustomerList.customers) {
                        f.write(customer.getUsername() + ", " + customer.getPassword() + ", " + customer.getPoints() + "\n");
                    }
                    f.close();
                    
                    FileWriter f2 = new FileWriter(new File("books.txt"), false);
                    for (Book book : BooksList.books) {
                        f2.write(book.getTitle() + ", " + book.getPrice() + "\n");
                    }
                    f2.close();
                } catch (IOException exception) {
                    System.out.println("Cannot write to file");
                }
            } else if (result == ButtonType.NO) {
                e.consume(); // leave original window on
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
