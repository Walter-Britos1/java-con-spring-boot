package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.models.Episode;
import com.aluracursos.screenmatch.models.Genre;
import com.aluracursos.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitleContainsIgnoreCase(String nameSerie);

    List<Serie> findTop5ByOrderByRatingsDesc();

    List<Serie> findByGenre(Genre genre);

    @Query("SELECT s FROM Serie s WHERE s.totalSeasons <= :totalSeasons AND s.ratings >= :ratings")
    List<Serie> seriesBySeasonAndRatings(int totalSeasons, Double ratings);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE e.title ILIKE %:titleEpisode%")
    List<Episode> searchEpisodeByTitle(String titleEpisode);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie ORDER BY e.rating DESC LIMIT 5")
    List<Episode> findTop5(Serie serie);
}
