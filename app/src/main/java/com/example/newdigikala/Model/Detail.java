package com.example.newdigikala.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Detail{
	private String id;
	private String image;
	private String pprice;
	private String price;
	private String properties;
	private String rating;
	private String rating_item;
	private String weight;
	private String title;
	private String colors;
	private String introduction;
	private String garantee;
	@SerializedName("comments")
	private List<Comment> comments;
	@SerializedName("fav")
	private List<DetailFavorite> favList;



	public List<DetailFavorite> getFavList() {
		return favList;
	}

	public void setFavList(List<DetailFavorite> favList) {
		this.favList = favList;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setPprice(String pprice){
		this.pprice = pprice;
	}

	public String getPprice(){
		return pprice;
	}

	public void setGarantee(String garantee){
		this.garantee = garantee;
	}

	public String getGarantee(){
		return garantee;
	}

	public void setRating(String rating){
		this.rating = rating;
	}

	public String getRating(){
		return rating;
	}

	public void setWeight(String weight){
		this.weight = weight;
	}

	public String getWeight(){
		return weight;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setColors(String colors){
		this.colors = colors;
	}

	public String getColors(){
		return colors;
	}

	public void setRatingItem(String rating_item){
		this.rating_item = rating_item;
	}

	public String getRatingItem(){
		return rating_item;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}

	public String getIntroduction(){
		return introduction;
	}

	public void setProperties(String properties){
		this.properties = properties;
	}

	public String getProperties(){
		return properties;
	}
}