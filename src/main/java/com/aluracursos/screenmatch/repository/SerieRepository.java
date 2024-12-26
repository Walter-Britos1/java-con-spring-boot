package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.models.Genre;
import com.aluracursos.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitleContainsIgnoreCase(String nameSerie);

    List<Serie> findTop5ByOrderByRatingsDesc();

    List<Serie> findByGenre(Genre genre);
}
