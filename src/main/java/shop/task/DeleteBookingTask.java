package shop.task;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.concurrent.Task;
import shop.ShopApplication;
import shop.model.User;

public class DeleteBookingTask extends Task<Boolean> {

	private User user;
	private Integer bookingId;

	public DeleteBookingTask(User user, Integer bookingId) {
		this.user = user;
		this.bookingId = bookingId;
	}

	@Override
	protected Boolean call() throws Exception {
		String url = ShopApplication.API_HOST + "/bookings/" + bookingId;
		HttpResponse<JsonNode> res = Unirest.delete(url).header("Authorization", user.jsonWebToken).asJson();
		return res.getStatus() == 200;
	}
}
