package shop.task;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import shop.ShopApplication;
import shop.model.Product;

import java.io.InputStream;

public class GetProductImageTask extends Task<Image> {

	Product product;

	public GetProductImageTask(Product product) {
		this.product = product;
	}

	@Override
	protected Image call() throws Exception {
		String url = ShopApplication.API_HOST  + "/products/" + product.id;
		HttpResponse<InputStream> resImage = Unirest.get(url).header("Accept", "application/octet-stream").asBinary();
		InputStream is = resImage.getBody();
		product.image = new Image(is);
		return product.image;
	}
}
