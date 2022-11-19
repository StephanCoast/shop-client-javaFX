package shop.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import shop.ShopApplication;
import shop.model.User;
import shop.task.PostLoginTask;

public class LoginController {

    public static User activeUser; // user who is currently logged in

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginFailure;

    public void loginAction(ActionEvent e1) {
        PostLoginTask loginTask = new PostLoginTask(username.getText(), password.getText());
        loginTask.setOnSucceeded((WorkerStateEvent e2) -> {

            activeUser = loginTask.getValue();
            if (activeUser == null) {
                // login failed
                loginFailure.setVisible(true);
                return;
            }

            // query the bookings of the active user
            ShopApplication.queryActiveUserBookings(null);

            // navigate to catalog view
            MainController.getInstance().changeView("catalog");
            MainController.getInstance().menuBtn.setVisible(true);
        });
        new Thread(loginTask).start();
    }

}