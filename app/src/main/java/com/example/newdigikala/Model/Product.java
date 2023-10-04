package com.example.newdigikala.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Product{
	@SerializedName("image")
	private String image;

	@SerializedName("price")
	private String price;

	@SerializedName("pprice")
	private String pprice;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("properties")
	private String properties;

	@SerializedName("filter_item")
	private String filterItem;

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getFilterItem() {
		return filterItem;
	}

	public void setFilterItem(String filterItem) {
		this.filterItem = filterItem;
	}

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

	public void setPprice(String pprice){
		this.pprice = pprice;
	}

	public String getPprice(){
		return pprice;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}
}