package com.qmul.nminoiu.tunein;

/**
 * Created by nicoleta on 17/12/2017.
 */
public class DoubleRow {
    private int imageId;
    private String title;
    private String subtitle;
    private String message;


    /**
     * Instantiates a new Double row.
     *
     * @param imageId  the image id
     * @param title    the title
     * @param subtitle the subtitle
     * @param message  the message
     */
    public DoubleRow(int imageId, String title, String subtitle, String message) {
        this.imageId = imageId;
        this.title = title;
        this.subtitle = subtitle;
        this.message = message;

    }

    /**
     * Gets image id.
     *
     * @return the image id
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * Sets image id.
     *
     * @param imageId the image id
     */
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Gets subtitle.
     *
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Sets subtitle.
     *
     * @param subtitle the subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

