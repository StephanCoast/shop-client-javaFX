package shop.controller;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MenuController {

    public static final String LOGOUT_ACTION = "logout";

    @FXML
    JFXListView menu;

    public void menuAction(MouseEvent e) {
        Node node = (Node) menu.getSelectionModel().getSelectedItem();
        if (node == null) {
            return;
        }
        String nextView = node.getId();
        if (nextView.equals(LOGOUT_ACTION)) {
            LoginController.activeUser = null;
            MainController.getInstance().menuBtn.setVisible(false);
            nextView = "login";
        }
        MainController.getInstance().changeView(nextView);
        MainController.getInstance().menuDrawer.toggle();
    }
}
