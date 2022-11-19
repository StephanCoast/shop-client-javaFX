package shop.controller;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import shop.ShopApplication;
import shop.model.Booking;
import shop.task.DeleteBookingTask;

public class BookingsController {

    @FXML
    TableView<Booking> bookingsTable;

    @FXML
    TableColumn bookingsTableDeleteBooking;

    @FXML
    protected void initialize() {
        bookingsTable.setItems(FXCollections.observableArrayList(LoginController.activeUser.bookings));

        bookingsTable.setFocusTraversable(false);
        bookingsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        bookingsTableDeleteBooking.setCellFactory(callback -> new TableCell<Booking, Integer>() {

            @Override
            public void updateItem(Integer bookingId, boolean empty) {
                super.updateItem(bookingId, empty);
                if (!empty) {
                    MaterialIconView deleteBtn = new MaterialIconView();
                    deleteBtn.setGlyphName("DELETE");
                    deleteBtn.getStyleClass().add("deleteBtn");
                    deleteBtn.setOnMouseClicked(e1 -> {
                        DeleteBookingTask deleteBookingTask = new DeleteBookingTask(LoginController.activeUser, bookingId);
                        deleteBookingTask.setOnSucceeded(e2 -> {
                            Booking booking = bookingsTable.getItems().stream().filter(b -> b.id == bookingId).findFirst().get();
                            getTableView().getItems().remove(booking);
                            LoginController.activeUser.bookings.remove(booking);
                        });
                        new Thread(deleteBookingTask).start();
                    });
                    setGraphic(deleteBtn);
                } else {
                    setGraphic(null);
                }
            }
        });

        // bind pane size to parent container
        bookingsTable.prefWidthProperty().bind(MainController.getInstance().viewHolder.getScene().widthProperty().subtract(60));
    }

    public void refreshAction() {
        Runnable r = () -> bookingsTable.setItems(FXCollections.observableArrayList(LoginController.activeUser.bookings));
        ShopApplication.queryActiveUserBookings(r);
    }
}
