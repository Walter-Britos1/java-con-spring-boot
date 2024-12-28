package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisode(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer numberEpisode,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String releaseDate
) {
    @Override
    public String toString() {
        return
                "title: " + title +
                "\n numberEpisode: " + numberEpisode +
                "\n rating: " + rating  +
                "\n releaseDate: " + releaseDate;
    }
}
