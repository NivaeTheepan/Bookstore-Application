 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 *
 * @author lalith
 */
public class OwnerScreen{
    
    static Scene ownerMenu;
    
    public static void mainScreen(Stage application, Scene mainScene) {
        // create a grid layout sized 640x400
        GridPane layout = new GridPane();
        ownerMenu = new Scene(layout, mainScene.getWidth(), mainScene.getHeight());
        
        // create the buttons for the owner screen
        Button books = new Button("Books");
        books.setTranslateX(300); books.setTranslateY(150);
        books.setOnAction(e -> BooksList.display(application    , ownerMenu));
        
        Button customers = new Button("Customers");
        customers.setTranslateX(288); customers.setTranslateY(200);    
        customers.setOnAction(e -> CustomerList.display(application, ownerMenu));
        
        Button logout = new Button("Logout");
        logout.setTranslateX(300); logout.setTranslateY(250);
        logout.setOnAction(e -> application.setScene(mainScene));
        
        layout.getChildren().addAll(books, customers, logout);

        application.setScene(ownerMenu);
    }
}
