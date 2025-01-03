package com.aluracursos.screenmatch.services;

import com.aluracursos.screenmatch.dto.EpisodeDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.models.Episode;
import com.aluracursos.screenmatch.models.Genre;
import com.aluracursos.screenmatch.models.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SerieServices {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> dataConverter(List<Serie> serie) {
        return serie.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitle(),
                        s.getTotalSeasons(),
                        s.getRatings(),
                        s.getGenre(),
                        s.getSynopsis(),
                        s.getPoster(),
                        s.getActors())).collect(Collectors.toList());
    }

    public List<SerieDTO> getAllSeries() {
        return dataConverter(repository.findAll());
    }

    public List<SerieDTO> getTop5() {
        return dataConverter(repository.findTop5ByOrderByRatingsDesc());
    }

    public List<SerieDTO> getLatestReleases() {
        return dataConverter(repository.latestReleases());
    }

    public SerieDTO getSeriesById(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitle(),
                    s.getTotalSeasons(),
                    s.getRatings(),
                    s.getGenre(),
                    s.getSynopsis(),
                    s.getPoster(),
                    s.getActors());
        }
        return null;
    }

    public List<EpisodeDTO> getAllSeason(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodes().stream().map(e -> new EpisodeDTO(
                    e.getSeason(),
                    e.getTitle(),
                    e.getNumberEpisode()
            )).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodeDTO> getSeasonByNumber(Long id, Long numeroTemporada) {
        return repository.getSeasonByNumber(id, numeroTemporada).stream()
                .map(e -> new EpisodeDTO(
                        e.getSeason(),
                        e.getTitle(),
                        e.getNumberEpisode()
                )).collect(Collectors.toList());
    }

    public List<SerieDTO> getSeriesByCategry(String nombreGenero) {
        Genre genre = Genre.fromEspanol(nombreGenero);
        return dataConverter(repository.findByGenre(genre));
    }
}
