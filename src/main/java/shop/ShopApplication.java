package shop;

import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shop.controller.LoginController;
import shop.model.Product;
import shop.model.User;
import shop.task.GetProductImageTask;
import shop.task.GetProductsTask;
import shop.task.GetUserDetailsTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShopApplication extends Application {

    public static final String APP_TITLE = "Yacht Shop";
    public static final String API_HOST = "http://localhost:8080";
    public static final List<Product> PRODUCTS = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        Node main = loadFXML("view/main.fxml");
        Scene scene = new Scene((Parent) main, 1024, 768);
        stage.setScene(scene);
        stage.setTitle(APP_TITLE);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();

        // query all products from REST API
        GetProductsTask productsTask = new GetProductsTask();

        productsTask.setOnRunning((successEvent) -> {
            System.out.println("loading products...");
        });


        //Erst Task definieren incl. WorkerStateEvent als Flag, um zu wissen, wann fertig
        productsTask.setOnSucceeded((WorkerStateEvent e) -> {

            System.out.println("products loaded.");
            PRODUCTS.addAll(productsTask.getValue());

            // query product images from REST API
            for (Product product : PRODUCTS) {
                GetProductImageTask imageTask = new GetProductImageTask(product);
                imageTask.setOnSucceeded((WorkerStateEvent e2) -> {
                    product.image = imageTask.getValue();
                });
                new Thread(imageTask).start();
            }
        });
        //Tasks in eigenem Thread ausführen
        new Thread(productsTask).start();
    }

    public static void queryActiveUserBookings(Runnable runnable) {
        User user = LoginController.activeUser;
        GetUserDetailsTask bookingsTask = new GetUserDetailsTask(user);
        bookingsTask.setOnSucceeded((WorkerStateEvent e2) -> {
            user.id = bookingsTask.getValue().id;
            user.bookings.clear();
            user.bookings.addAll(bookingsTask.getValue().bookings);

            if (runnable != null) {
                new Thread(runnable).start();
            }
        });
        new Thread(bookingsTask).start();
    }

    public static Node loadFXML(String fxmlFilename) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ShopApplication.class.getResource(fxmlFilename));
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}