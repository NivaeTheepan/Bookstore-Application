package bookstoreapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lalith
 */
public class Customer {
    private String username;
    private String password;
    private int points;
    private String status;
    //private ObservableList<Book> books, cart; // arraylist for javafx follows the state observer pattern
    private ObservableList<Book> cart; // arraylist for javafx follows the state observer pattern

    public Customer(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
        this.status = "Silver";
        //books = FXCollections.observableArrayList();
        cart = FXCollections.observableArrayList();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public ObservableList<Book> getCart() {
        return cart;
    }

    public String getStatus() {
        return status;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /*public ObservableList<Book> getBooks() {
        return books;
    }*/
    
    public void buyBook(Book book) {
        this.setPoints(this.getPoints() + (int)(Math.round(book.getPrice())) * 10);
        //cart.remove(book);
        //books.add(book);
    }
    
    /*public void removeBook(Book book) { 
        books.remove(book);
    }*/
    
    public void buyWithPoints(Book book) throws IllegalArgumentException {
        if ((this.getPoints() - (int)(Math.round(book.getPrice())) * 100) > 0) {
            this.setPoints(this.getPoints() - (int)(Math.round(book.getPrice())) * 100);
        } else {
            throw new IllegalArgumentException("Cannot redeem points");
        }
    }
    
    public void addToCart(Book book) {
        cart.add(book);
    }
    
    public void removeFromCart(Book book) {
        cart.remove(book);
    }
    
    public void clearCart() {
        cart.clear();
    }
    
    // this is for testing purposes
    public String booksToString(ObservableList<Book> collection) {
        String result = "";
        int count = 1;
        for (Book book : collection) {
            result += count++ + ". Title: " + book.getTitle() + " Price: " + book.getPrice() + "\n";
        }
        return result;
    }
}