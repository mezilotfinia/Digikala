package com.example.newdigikala.Model;

import com.google.gson.annotations.SerializedName;

public class Banner{

	@SerializedName("id")
	private String id;

	@SerializedName("pic")
	private String pic;

	@SerializedName("type")
	private String type;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPic(String pic){
		this.pic = pic;
	}

	public String getPic(){
		return pic;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}