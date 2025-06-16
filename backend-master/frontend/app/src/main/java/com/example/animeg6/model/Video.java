package com.example.animeg6.model;

import java.io.Serializable;

public class Video implements Serializable {

    private int idanime;
    private int episode;
    private String url;
    private String image;

    public Video(int idanime, int episode, String url, String image) {
        this.idanime = idanime;
        this.episode = episode;
        this.url = url;
        this.image = image;
    }

    public Video() {
    }

    public int getIdanime() {
        return idanime;
    }

    public void setIdanime(int idanime) {
        this.idanime = idanime;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Video{" +
                "id=" + idanime +
                ", episode=" + episode +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}
