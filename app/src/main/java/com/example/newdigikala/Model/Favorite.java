package com.example.newdigikala.Model;

import com.google.gson.annotations.SerializedName;

public class Favorite{

	@SerializedName("image")
	private String image;

	@SerializedName("fav_id")
	private String favId;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("title")
	private String title;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setFavId(String favId){
		this.favId = favId;
	}

	public String getFavId(){
		return favId;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}
}