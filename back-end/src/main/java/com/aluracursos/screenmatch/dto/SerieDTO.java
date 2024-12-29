package com.aluracursos.screenmatch.dto;

import com.aluracursos.screenmatch.models.Genre;

public record SerieDTO(
        String title,
        Integer totalSeasons,
        Double ratings,
        Genre genre,
        String synopsis,
        String poster,
        String actors
) {
}
