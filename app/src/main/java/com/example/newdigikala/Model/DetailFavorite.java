package com.example.newdigikala.Model;


import com.google.gson.annotations.SerializedName;

public class DetailFavorite {

    @SerializedName("fav_id")
    private String mFavId;
    @SerializedName("fav_idproduct")
    private String mFavIdproduct;
    @SerializedName("fav_image")
    private String mFavImage;
    @SerializedName("fav_title")
    private String mFavTitle;
    @SerializedName("fav_userId")
    private String mFavUserId;

    public String getFavId() {
        return mFavId;
    }

    public void setFavId(String favId) {
        mFavId = favId;
    }

    public String getFavIdproduct() {
        return mFavIdproduct;
    }

    public void setFavIdproduct(String favIdproduct) {
        mFavIdproduct = favIdproduct;
    }

    public String getFavImage() {
        return mFavImage;
    }

    public void setFavImage(String favImage) {
        mFavImage = favImage;
    }

    public String getFavTitle() {
        return mFavTitle;
    }

    public void setFavTitle(String favTitle) {
        mFavTitle = favTitle;
    }

    public String getFavUserId() {
        return mFavUserId;
    }

    public void setFavUserId(String favUserId) {
        mFavUserId = favUserId;
    }

}
