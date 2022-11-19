package shop.model;

import javafx.scene.image.Image;

public class Product {
	public Integer id;
	public String title;
	public String description;
	public Integer price;
	public Boolean instock;
	public transient Image image;
}
