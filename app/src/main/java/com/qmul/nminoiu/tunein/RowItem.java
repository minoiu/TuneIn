package com.qmul.nminoiu.tunein;

/**
 * Created by nminoiu on 15/10/2017.
 */
public class RowItem {
    private int imageId;
    private String title;

    /**
     * Instantiates a new Row item.
     *
     * @param imageId the image id
     * @param title   the title
     */
    public RowItem(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
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

}
