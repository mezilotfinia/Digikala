package com.example.newdigikala.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable {

	@SerializedName("negative")
	private String negative;

	@SerializedName("passage")
	private String passage;

	@SerializedName("param")
	private String param;

	@SerializedName("id")
	private String id;

	@SerializedName("positive")
	private String positive;

	@SerializedName("suggest")
	private String suggest;

	@SerializedName("title")
	private String title;

	@SerializedName("likecount")
	private String likecount;

	@SerializedName("user")
	private String user;

	@SerializedName("dislikecount")
	private String dislikecount;



	public void setNegative(String negative){
		this.negative = negative;
	}

	public String getNegative(){
		return negative;
	}

	public void setPassage(String passage){
		this.passage = passage;
	}

	public String getPassage(){
		return passage;
	}

	public void setParam(String param){
		this.param = param;
	}

	public String getParam(){
		return param;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPositive(String positive){
		this.positive = positive;
	}

	public String getPositive(){
		return positive;
	}

	public void setSuggest(String suggest){
		this.suggest = suggest;
	}

	public String getSuggest(){
		return suggest;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setLikecount(String likecount){
		this.likecount = likecount;
	}

	public String getLikecount(){
		return likecount;
	}

	public void setUser(String user){
		this.user = user;
	}

	public String getUser(){
		return user;
	}

	public void setDislikecount(String dislikecount){
		this.dislikecount = dislikecount;
	}

	public String getDislikecount(){
		return dislikecount;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.negative);
		dest.writeString(this.passage);
		dest.writeString(this.param);
		dest.writeString(this.id);
		dest.writeString(this.positive);
		dest.writeString(this.suggest);
		dest.writeString(this.title);
		dest.writeString(this.likecount);
		dest.writeString(this.user);
		dest.writeString(this.dislikecount);
	}

	public void readFromParcel(Parcel source) {
		this.negative = source.readString();
		this.passage = source.readString();
		this.param = source.readString();
		this.id = source.readString();
		this.positive = source.readString();
		this.suggest = source.readString();
		this.title = source.readString();
		this.likecount = source.readString();
		this.user = source.readString();
		this.dislikecount = source.readString();
	}

	public Comment() {
	}

	protected Comment(Parcel in) {
		this.negative = in.readString();
		this.passage = in.readString();
		this.param = in.readString();
		this.id = in.readString();
		this.positive = in.readString();
		this.suggest = in.readString();
		this.title = in.readString();
		this.likecount = in.readString();
		this.user = in.readString();
		this.dislikecount = in.readString();
	}

	public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
		@Override
		public Comment createFromParcel(Parcel source) {
			return new Comment(source);
		}

		@Override
		public Comment[] newArray(int size) {
			return new Comment[size];
		}
	};
}