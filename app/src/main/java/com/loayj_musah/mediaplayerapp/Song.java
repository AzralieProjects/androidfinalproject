package com.loayj_musah.mediaplayerapp;


import android.net.Uri;

public class Song {



    private String  Name;
    private String Url;
    private String Date;



    public void setDate(String date) {
        Date = date;
    }

    public Song(String name, String url, String date, Uri uri) {
        this.Name = name;
        this.Url = url;
        this.Date = date;

    }
    public Song() {


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
