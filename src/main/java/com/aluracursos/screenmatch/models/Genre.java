package com.aluracursos.screenmatch.models;

public enum Genre {
    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    ANIMATION("Animation");

    private String genreOmd;

    Genre(String genreOmd) {
        this.genreOmd = genreOmd;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genreOmd.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("No se encontro ningun genero: " + text);
    }
}
