package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(
        @JsonAlias("Title") String title,
        Integer totalSeasons,
        @JsonAlias("imdbRating") String ratings,
        @JsonAlias("Genre") String genre,
        @JsonAlias("Plot") String synopsis,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Actors") String actors
) {
    @Override
    public String toString() {
        return "Title: " + title +
                "\nTotal Seasons: " + totalSeasons +
                "\nRatings: " + ratings +
                "\nGenre: " + genre +
                "\nPlot: " + synopsis +
                "\nPoster: " + poster +
                "\nActors: " + actors;
    }
}
