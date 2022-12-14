package shop.task;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.concurrent.Task;
import shop.ShopApplication;
import shop.model.User;

public class PostLoginTask extends Task<User> {

	private String username;

	private String password;

	public PostLoginTask(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	protected User call() {
		String url = ShopApplication.API_HOST + "/login";
		String json = "{\"name\": \"" + username + "\", \"passwordHash\": \"" + password + "\"}";

		try {
			HttpResponse<String> res = Unirest.post(url).header("Content-Type", "application/json").body(json).asString();
			if (res.getStatus() != 403) {
				String jsonWebToken = res.getHeaders().getFirst("Authorization");
				return new User(username, jsonWebToken);
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return null;
	}
}
