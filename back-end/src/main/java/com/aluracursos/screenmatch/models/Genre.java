package com.aluracursos.screenmatch.models;

public enum Genre {
    ACTION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDY("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crimen"),
    ANIMATION("Animation", "Animación");

    private String genreOmd;

    private String genreEspanol;

    Genre(String genreOmd, String genreEspanol) {
        this.genreOmd = genreOmd;
        this.genreEspanol = genreEspanol;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genreOmd.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("No se encontro ningun genero: " + text);
    }

    public static Genre fromEspanol(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genreEspanol.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("No se encontro ningun genero: " + text);
    }
}
