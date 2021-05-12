package com.carriergistics.eld.dvir;

import android.graphics.Bitmap;
import android.net.Uri;

public class Issue {

    private String remarks;
    private String picturePath;
    private boolean safe;
    private boolean resolved = false;
    private String resolverName;
    private String resolveNotes;
    private boolean driverSigned;

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

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public String getResolverName() {
        return resolverName;
    }

    public void setResolverName(String resolverName) {
        this.resolverName = resolverName;
    }

    public String getResolveNotes() {
        return resolveNotes;
    }

    public void setResolveNotes(String resolveNotes) {
        this.resolveNotes = resolveNotes;
    }

    public boolean isDriverSigned() {
        return driverSigned;
    }

    public void setDriverSigned(boolean driverSigned) {
        this.driverSigned = driverSigned;
    }
}
