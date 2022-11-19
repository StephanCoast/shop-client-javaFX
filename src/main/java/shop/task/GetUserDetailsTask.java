package shop.task;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.concurrent.Task;
import shop.ShopApplication;
import shop.model.User;

public class GetUserDetailsTask extends Task<User> {

	private User user;

	public GetUserDetailsTask(User user) {
		this.user = user;
	}

	@Override
	protected User call() throws Exception {
		String url = ShopApplication.API_HOST + "/users/" + user.name;
		HttpResponse<JsonNode> resJson = Unirest.get(url).header("Accept", "application/json").header("Authorization", user.jsonWebToken).asJson();
		String json = resJson.getBody().toString();

        User user = new Gson().fromJson(json, User.class);
		return user;
	}
}
