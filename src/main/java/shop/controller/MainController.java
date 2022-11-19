package shop.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import shop.ShopApplication;

public class MainController {

    // singleton
    private static MainController instance;

    // singleton access
    public static MainController getInstance() {
        return instance;
    }

    @FXML
    StackPane viewHolder;

    @FXML
    Label title;

    @FXML
    JFXDrawersStack drawersStack;

    @FXML
    MaterialIconView menuBtn;

    JFXDrawer menuDrawer = new JFXDrawer();

    @FXML
    public void initialize() {
        instance = this;
        title.setText(ShopApplication.APP_TITLE);

        // menu on the left side
        menuDrawer.setSidePane(ShopApplication.loadFXML("view/menu.fxml"));
        menuDrawer.setDefaultDrawerSize(250);
        menuBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> drawersStack.toggle(menuDrawer));
    }

    public void changeView(String fxmlFilename) {
        Node view = ShopApplication.loadFXML("view/" + fxmlFilename + ".fxml");
        viewHolder.getChildren().setAll(view); // clears the list of child elements and adds the view as a new child element
    }
}
