package com.example.newdigikala.Model;

import com.google.gson.annotations.SerializedName;

public class Basket{

	@SerializedName("image")
	private String image;

	@SerializedName("price")
	private String price;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("basket_id")
	private String basketId;

	@SerializedName("guarantee")
	private String guarantee;

	@SerializedName("title")
	private String title;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setBasketId(String basketId){
		this.basketId = basketId;
	}

	public String getBasketId(){
		return basketId;
	}

	public void setGuarantee(String guarantee){
		this.guarantee = guarantee;
	}

	public String getGuarantee(){
		return guarantee;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}
}