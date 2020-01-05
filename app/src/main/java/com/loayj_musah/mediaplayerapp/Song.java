package com.loayj_musah.mediaplayerapp;



public class Song {
    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    private String  Name,Url;
    Boolean like;

    public Song(String name, String url, Boolean like) {
        Name = name;
        Url = url;
        this.like = like;
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

    public Song(String name, String url) {
        Name = name;
        Url = url;
    }
}
