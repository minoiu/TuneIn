package com.qmul.nminoiu.tunein;

/**
 * Created by nminoiu on 05/07/2017.
 */

public class NowPlayingItem {
    private int id;
    private String name, song, image, profilePic, timeStamp, url;

    public NowPlayingItem(){
    }

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

    public NowPlayingItem(String song){
        this.song = song;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


