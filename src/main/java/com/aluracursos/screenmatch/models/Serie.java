package com.aluracursos.screenmatch.models;

import com.aluracursos.screenmatch.services.ApiChatGTP;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Entity
@Table(name = "Series")

public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String title;
    private Integer totalSeasons;
    private Double ratings;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String synopsis;
    private String poster;
    private String actors;
    @Transient
    private List<Episode> episodes;

    public Serie(DataSeries dataSeries) {
        this.title = dataSeries.title();
        this.totalSeasons = dataSeries.totalSeasons();
        this.ratings = OptionalDouble.of(Double.valueOf(dataSeries.ratings())).orElse(0);
        this.genre = Genre.fromString(dataSeries.genre().split(",")[0].trim());
        // con traduccion
//        this.synopsis = ApiChatGTP.translate(dataSeries.synopsis());
        // sin traduccion
        this.synopsis = dataSeries.synopsis();
        this.poster = dataSeries.poster();
        this.actors = dataSeries.actors();
    }

    @Override
    public String toString() {
        return "\nGenre: " + genre +
                "\nTitle: " + title +
                "\nTotal Seasons: " + totalSeasons +
                "\nRatings: " + ratings +
                "\nSynopsis: " + synopsis +
                "\nPoster: " + poster +
                "\nActors: " + actors;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRatings() {
        return ratings;
    }

    public void setRatings(Double ratings) {
        this.ratings = ratings;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }
}
