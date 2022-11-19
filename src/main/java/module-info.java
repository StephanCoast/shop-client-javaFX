module de.osmi.pf.shop {

    requires javafx.fxml;
    requires de.jensd.fx.glyphs.materialicons;
    requires com.jfoenix;
    requires unirest.java;
    requires com.google.gson;
    requires json.path;
    requires javafx.graphics;
    requires javafx.controls;

    // opens means accessible during run time
    opens shop.controller to javafx.fxml;
    opens shop.model to com.google.gson;

    //exports means accessible during compile and run time!
    exports shop;
    exports shop.model;

}