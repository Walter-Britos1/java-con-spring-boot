package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record episodeData(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer numberEpisode,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String releaseDate
) {

}
