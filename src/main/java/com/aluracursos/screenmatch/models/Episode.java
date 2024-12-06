package com.aluracursos.screenmatch.models;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer numberEpisode;
    private Double reating;
    private LocalDate releaseData;

    public Episode(Integer number, DataEpisode d) {
        this.season = number;
        this.title = d.title();
        this.numberEpisode = d.numberEpisode();
        try {
            this.reating = Double.valueOf(d.rating());
        }catch (NumberFormatException e) {
            this.reating = 0.0;
        }
        try {
            this.releaseData = LocalDate.parse(d.releaseDate());
        } catch (DateTimeParseException e) {
            this.releaseData = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberEpisode() {
        return numberEpisode;
    }

    public void setNumberEpisode(Integer numberEpisode) {
        this.numberEpisode = numberEpisode;
    }

    public Double getReating() {
        return reating;
    }

    public void setReating(Double reating) {
        this.reating = reating;
    }

    public LocalDate getReleaseData() {
        return releaseData;
    }

    public void setReleaseData(LocalDate releaseData) {
        this.releaseData = releaseData;
    }

    @Override
    public String toString() {
        return  "season: " + season +
                ", title:' " + title + '\'' +
                ", numberEpisode: " + numberEpisode +
                ", reating: " + reating +
                ", releaseData: " + releaseData;
    }
}
