package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(
        @JsonAlias("Title") String title,
        Integer totalSeasons,
        @JsonAlias("imdbRating") String ratings
) {
    @Override
    public String toString() {
        return "title: " + title +
                "\ntotalSeasons: " + totalSeasons +
                "\nratings: " + ratings;
    }
}
