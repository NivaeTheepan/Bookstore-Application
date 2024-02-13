/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author lalith
 */
public class BuyScreen{
    
    static Scene finalBuyMenu;
        
    public static void display(Stage application, double cost, Customer customer, double[] dimensions) {
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        
        Label cost_label = new Label("Total Cost: " + Double.toString(cost));
        
        HBox customerLayout = new HBox();
        customerLayout.setSpacing(30);
        customerLayout.setAlignment(Pos.CENTER);
        Label points_label = new Label("Points: " + Integer.toString(customer.getPoints()));
        Label status_label = new Label("Status: " + customer.getStatus());
        
        Button logout = new Button("Logout");
        logout.setOnAction(e -> application.setScene(BookStoreApplication.loginScreen));
        
        customerLayout.getChildren().addAll(points_label, status_label);
        
        layout.getChildren().addAll(cost_label, customerLayout, logout);
        
        finalBuyMenu = new Scene(layout, dimensions[0], dimensions[1]);
        
        application.setScene(finalBuyMenu);
    }    
}
