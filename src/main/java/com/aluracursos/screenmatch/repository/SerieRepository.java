package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitleContainsIgnoreCase(String nameSerie);
}
