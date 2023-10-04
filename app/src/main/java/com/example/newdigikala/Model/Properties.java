package com.example.newdigikala.Model;

import com.google.gson.annotations.SerializedName;

public class Properties{

	@SerializedName("title")
	private String title;

	@SerializedName("value")
	private String value;

	@SerializedName("second")
	private String second;

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}
}