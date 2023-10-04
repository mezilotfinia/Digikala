package com.example.newdigikala.Model;

import com.google.gson.annotations.SerializedName;

public class HistoryModel{

	@SerializedName("date")
	private String date;

	@SerializedName("price")
	private String price;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("id")
	private String id;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}
}