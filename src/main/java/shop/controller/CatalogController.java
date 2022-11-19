package shop.controller;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shop.ShopApplication;
import shop.model.Booking;
import shop.model.Product;
import shop.task.PostBookingTask;

public class CatalogController {

    @FXML
    private FlowPane catalogPane;

    @FXML
    protected void initialize() {

        // display all products in catalog pane
        catalogPane.getChildren().clear();

        for (Product product : ShopApplication.PRODUCTS) {

            VBox vbox = new VBox();
            vbox.getStyleClass().add("product");
            vbox.setAlignment(Pos.CENTER);

            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(280);
            imageView.setImage(product.image);

            Label title = new Label(product.title);
            title.getStyleClass().add("bold");

            Label price = new Label(product.price + " MEUR");

            String productShortDesc = product.description.substring(0, product.description.indexOf(".") + 1);
            Label description = new Label(productShortDesc);
            description.getStyleClass().add("desc");

            JFXButton bookingBtn = new JFXButton();
            bookingBtn.setText(product.instock ? "Buchen".toUpperCase() : "Nicht verfügbar".toUpperCase());
            if (!product.instock) {
                bookingBtn.setDisable(true);
            }
            bookingBtn.setOnAction((ActionEvent e1) -> {
                // post a new booking
                Booking newBooking = new Booking(product.price, product, LoginController.activeUser);
                PostBookingTask postBookingTask = new PostBookingTask(LoginController.activeUser, newBooking);
                postBookingTask.setOnSucceeded(e2 -> {
                    LoginController.activeUser.bookings.add(postBookingTask.getValue());
                    showAcknowledgementDialog(newBooking);
                });
                new Thread(postBookingTask).start();
            });

            vbox.getChildren().add(imageView);
            vbox.getChildren().add(title);
            vbox.getChildren().add(price);
            vbox.getChildren().add(bookingBtn);
            vbox.getChildren().add(description);
            catalogPane.getChildren().add(vbox);

            // bind pane size to parent container
            catalogPane.prefWrapLengthProperty().bind(MainController.getInstance().viewHolder.getScene().widthProperty().subtract(60));
        }
    }

    void showAcknowledgementDialog(Booking booking) {
        JFXAlert alert = new JFXAlert((Stage) catalogPane.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Buchung bestätigt"));
        layout.setBody(new Label(booking.product.title + " für " + booking.bookingPrice + " MEUR gebucht."));
        JFXButton closeButton = new JFXButton("OK");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }
}
