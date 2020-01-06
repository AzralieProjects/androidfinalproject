package com.loayj_musah.mediaplayerapp;


import android.net.Uri;

public class Song {
    public Boolean getLike() {
        return Like;
    }

    public void setLike(Boolean like) {
        this.Like = like;
    }

    private String  Name;
    private String Url;
    private String Date;
    private Boolean Like;



    public void setDate(String date) {
        Date = date;
    }

    public Song(String name, String url, String date, Uri uri) {
        Name = name;
        Url = url;
        Date = date;

    }




    public String getName() {
        return Name;
    }

    public String getUrl() {
        return Url;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setUrl(String url) {
        Url = url;
    }
    public String getDate() {
        return Date;
    }


}
