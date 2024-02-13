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
public class BooksList {
    
    static Scene booksListMenu;
    static ObservableList<Book> books = FXCollections.observableArrayList();
    
    public static void display(Stage application, Scene back) {
        TableView table = new TableView();
        VBox layout = new VBox(table); // creates a vbox layout
        
        Button bck = new Button("Back");
        bck.setOnAction(e -> application.setScene(back));
        
        Button del = new Button("Delete");
        del.setOnAction(e -> {
            Book bookDelete = (Book)table.getSelectionModel().getSelectedItem();
            deleteBook(books, bookDelete);
            table.refresh();
        });
        
        Label title_label = new Label("Title: ");
        TextField titleField = new TextField();
        Label price_label = new Label("Price: ");
        TextField priceField = new TextField();
        
        HBox titleHBox = new HBox(title_label, titleField); // horizontal version of vbox
        HBox priceHBox = new HBox(price_label, priceField);
        
        Button add = new Button("Add");
        add.setOnAction(e -> {
            try {
                Double price = Double.parseDouble(priceField.getText());
                if (!((titleField.getText().isEmpty() || priceField.getText().isEmpty()))) {
                    Book bookAdd = new Book(titleField.getText(), Double.parseDouble(priceField.getText()));
                    addBook(bookAdd);
                    titleField.clear(); priceField.clear();
                }
                table.refresh();
            } catch (NumberFormatException exp) {
                Alert incorrectAdd = new Alert(AlertType.ERROR);
                incorrectAdd.setTitle("Invalid Input");
                incorrectAdd.setHeaderText("Price needs to be a number.");
                incorrectAdd.setContentText(null);
                incorrectAdd.showAndWait(); 
            }
        });
        
        // create the table columns
        TableColumn<Customer, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Customer, String> priceColumn = new TableColumn<>("Price ($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        table.setItems(books);
        table.getColumns().addAll(titleColumn, priceColumn);
        
        layout.getChildren().addAll(bck, del, titleHBox, priceHBox, add);
        
        booksListMenu = new Scene(layout, 640, 400);
        application.setScene(booksListMenu);
    }
    
    public static void retrieveBooksFromFile(String file, ObservableList<Book> booksList) {
        try {
            Scanner in = new Scanner(new File(file));
            String[] line;
            booksList.clear();
            while (in.hasNextLine()) {
                line = in.nextLine().split(", ");
                booksList.add(new Book(line[0], Double.parseDouble(line[1])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    
    public static void deleteBook(ObservableList<Book> booksList, Book book) {
        booksList.remove(book);
    }
    
    public static void addBook(Book book) {
        boolean existingBook = false;
        for (Book bookInList: books) {
            if(bookInList.getTitle().equals(book.getTitle())) {
                existingBook = true;
                break;
            }
        }
        
        if(!existingBook) {
            books.add(book);
        } else {
            Alert existingUser = new Alert(AlertType.ERROR);
            existingUser.setHeaderText("This book already exists.");
            existingUser.setContentText(null);
            existingUser.showAndWait();
        }
    }
}
