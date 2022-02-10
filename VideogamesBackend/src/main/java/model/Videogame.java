package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Videogame.forUser", query = "select v from Videogame v where :userId in (select u.id from v.users u)"),
        @NamedQuery(name = "Videogame.removeGame", query = "delete from Videogame v where :gameId = v.id and :userId in (select u.id from v.users u)")
})

public class Videogame {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private float rating;
    private int metacritic;
    private String image;

    @ManyToMany(mappedBy = "videogames")
    @JsonIgnore
    public List<VideogameUser> users = new ArrayList<>();

    @ManyToMany
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany
    private List<Platform> platforms = new ArrayList<>();

    public Videogame() {
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(int metacritic) {
        this.metacritic = metacritic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<VideogameUser> getUsers() {
        return users;
    }

    public void setUsers(List<VideogameUser> users) {
        this.users = users;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }
}
