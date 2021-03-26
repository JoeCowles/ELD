package com.carriergistics.eld.dvir;

import android.graphics.Bitmap;
import android.net.Uri;

public class Issue {

    private String remarks;
    private String picturePath;
    private boolean safe;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picture) {
        this.picturePath = picture;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}
