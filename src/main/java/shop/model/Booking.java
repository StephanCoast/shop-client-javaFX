package shop.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {
    public Integer id;
    public Date date = new Date();
    public Integer bookingPrice;
    public Product product;
    public User user;

    public Booking(Integer bookingPrice, Product product, User user) {
        this.bookingPrice = bookingPrice;
        this.product = product;
        this.user = user;
    }

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public StringProperty productTitleProperty() {
        return new SimpleStringProperty(product.title);
    }

    public StringProperty bookingDateProperty() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return new SimpleStringProperty(df.format(date));
    }

    public IntegerProperty bookingPriceProperty() {
        return new SimpleIntegerProperty(bookingPrice);
    }
}
