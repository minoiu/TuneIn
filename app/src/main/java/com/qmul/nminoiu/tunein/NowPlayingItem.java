package com.qmul.nminoiu.tunein;

/**
 * Created by nminoiu on 05/07/2017.
 */
public class NowPlayingItem {
    private int id;
    private String name, song, image, profilePic, timeStamp, url;

    /**
     * Instantiates a new Now playing item.
     */
    public NowPlayingItem(){
    }

    /**
     * Instantiates a new Now playing item.
     *
     * @param id         the id
     * @param name       the name
     * @param image      the image
     * @param song       the song
     * @param profilePic the profile pic
     * @param timeStamp  the time stamp
     * @param url        the url
     */
    public NowPlayingItem(int id, String name, String image, String song,
                          String profilePic, String timeStamp, String url){
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.song = song;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
    }

    /**
     * Instantiates a new Now playing item.
     *
     * @param song the song
     */
    public NowPlayingItem(String song){
        this.song = song;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets imge.
     *
     * @return the imge
     */
    public String getImge() {
        return image;
    }

    /**
     * Sets imge.
     *
     * @param image the image
     */
    public void setImge(String image) {
        this.image = image;
    }

    /**
     * Gets song.
     *
     * @return the song
     */
    public String getSong() {
        return song;
    }

    /**
     * Sets song.
     *
     * @param song the song
     */
    public void setSong(String song) {
        this.song = song;
    }

    /**
     * Gets profile pic.
     *
     * @return the profile pic
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Sets profile pic.
     *
     * @param profilePic the profile pic
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * Gets time stamp.
     *
     * @return the time stamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets time stamp.
     *
     * @param timeStamp the time stamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}


