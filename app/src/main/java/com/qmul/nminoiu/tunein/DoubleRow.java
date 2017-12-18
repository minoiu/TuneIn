package com.qmul.nminoiu.tunein;

/**
 * Created by nicoleta on 17/12/2017.
 */

public class DoubleRow {
    private int imageId;
    private String title;
    private String subtitle;
    private String message;


    public DoubleRow(int imageId, String title, String subtitle, String message) {
        this.imageId = imageId;
        this.title = title;
        this.subtitle = subtitle;
        this.message = message;

    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

