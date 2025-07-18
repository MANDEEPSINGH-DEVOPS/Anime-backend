package abp.project.anime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

public class Anime {
    private int id;
    private String name;
    private String description;
    private String type;
    private int year;
    private String image;
    private String originalname;
    private String rating;
    private String demography;
    private String genre;
    private String image2;
    private String image3;
    private int active;
    private boolean favorito;

    public Anime(int id, String name, String description, String type, int year, String image, String originalname, String rating, String demography, String genre, String image2, String image3, int active, boolean favorito) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.year = year;
        this.image = image;
        this.originalname = originalname;
        this.rating = rating;
        this.demography = demography;
        this.genre = genre;
        this.image2 = image2;
        this.image3 = image3;
        this.active = active;
        this.favorito = favorito;
    }

    public Anime() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOriginalname() {
        return originalname;
    }

    public void setOriginalname(String originalname) {
        this.originalname = originalname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDemography() {
        return demography;
    }

    public void setDemography(String demography) {
        this.demography = demography;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", year=" + year +
                ", image='" + image + '\'' +
                ", originalname='" + originalname + '\'' +
                ", rating='" + rating + '\'' +
                ", demography='" + demography + '\'' +
                ", genre='" + genre + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", active=" + active +
                ", favorito=" + favorito +
                '}';
    }
}
