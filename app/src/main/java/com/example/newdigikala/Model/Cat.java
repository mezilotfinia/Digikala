package com.example.newdigikala.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Cat implements Parcelable {

	@SerializedName("parent")
	private String parent;

	@SerializedName("id")
	private String id;

	@SerializedName("position")
	private String position;

	@SerializedName("title")
	private String title;

	public void setParent(String parent){
		this.parent = parent;
	}

	public String getParent(){
		return parent;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPosition(String position){
		this.position = position;
	}

	public String getPosition(){
		return position;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.parent);
		dest.writeString(this.id);
		dest.writeString(this.position);
		dest.writeString(this.title);
	}

	public void readFromParcel(Parcel source) {
		this.parent = source.readString();
		this.id = source.readString();
		this.position = source.readString();
		this.title = source.readString();
	}

	public Cat() {
	}

	protected Cat(Parcel in) {
		this.parent = in.readString();
		this.id = in.readString();
		this.position = in.readString();
		this.title = in.readString();
	}

	public static final Parcelable.Creator<Cat> CREATOR = new Parcelable.Creator<Cat>() {
		@Override
		public Cat createFromParcel(Parcel source) {
			return new Cat(source);
		}

		@Override
		public Cat[] newArray(int size) {
			return new Cat[size];
		}
	};
}