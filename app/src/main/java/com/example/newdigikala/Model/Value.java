
package com.example.newdigikala.Model;

import com.google.gson.annotations.SerializedName;


public class Value {

    @SerializedName("title")
    private String mTitle;

    private boolean isChecked;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


}
