package shop.task;

import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.concurrent.Task;
import shop.ShopApplication;
import shop.model.Booking;
import shop.model.User;

public class PostBookingTask extends Task<Booking> {

    private User user;
    private Booking booking;

    public PostBookingTask(User user, Booking booking) {
        this.user = user;
        this.booking = booking;
    }

    @Override
    protected Booking call() throws Exception {
        String url = ShopApplication.API_HOST + "/bookings";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("product", ShopApplication.API_HOST + "/products/" + booking.product.id);
        jsonObject.addProperty("user", ShopApplication.API_HOST + "/users/" + booking.user.id);
        jsonObject.addProperty("bookingPrice", booking.bookingPrice);

        HttpResponse<JsonNode> res = Unirest.post(url).header("Content-Type", "application/json").header("Authorization", user.jsonWebToken).body(jsonObject.toString()).asJson();

        String location = res.getHeaders().getFirst("Location");
        booking.id = Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));
        return booking;
    }
}
